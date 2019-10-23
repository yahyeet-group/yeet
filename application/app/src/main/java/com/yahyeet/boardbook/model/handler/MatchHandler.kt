package com.yahyeet.boardbook.model.handler

import com.yahyeet.boardbook.model.entity.Game
import com.yahyeet.boardbook.model.entity.Match
import com.yahyeet.boardbook.model.entity.MatchPlayer
import com.yahyeet.boardbook.model.handler.populator.MatchPlayerPopulator
import com.yahyeet.boardbook.model.handler.populator.MatchPopulator
import com.yahyeet.boardbook.model.repository.IGameRepository
import com.yahyeet.boardbook.model.repository.IGameRoleRepository
import com.yahyeet.boardbook.model.repository.IGameTeamRepository
import com.yahyeet.boardbook.model.repository.IMatchPlayerRepository
import com.yahyeet.boardbook.model.repository.IMatchRepository
import com.yahyeet.boardbook.model.repository.IRepositoryListener
import com.yahyeet.boardbook.model.repository.IUserRepository

import java.util.ArrayList
import java.util.concurrent.CompletableFuture
import java.util.concurrent.atomic.AtomicReference
import java.util.stream.Collectors

class MatchHandler(private val matchRepository: IMatchRepository,
                   private val matchPlayerRepository: IMatchPlayerRepository,
                   gameRepository: IGameRepository,
                   gameRoleRepository: IGameRoleRepository,
                   gameTeamRepository: IGameTeamRepository,
                   userRepository: IUserRepository) : IRepositoryListener<Match>, EntityHandler<Match> {
    private val listeners = ArrayList<MatchHandlerListener>()

    private val matchPopulator: MatchPopulator
    private val matchPlayerPopulator: MatchPlayerPopulator

    init {

        matchPopulator = MatchPopulator(gameRepository, matchPlayerRepository)
        matchPlayerPopulator = MatchPlayerPopulator(gameRoleRepository, gameTeamRepository, matchRepository, userRepository)
    }

    override fun find(id: String): CompletableFuture<Match> {
        return matchRepository.find(id).thenCompose(Function<Match, CompletionStage<Match>> { this.populate(it) })
    }

    override fun save(match: Match): CompletableFuture<Match> {
        checkMatchValidity(match)

        val savedMatchFuture = matchRepository.save(match)
        val savedMatchPlayersFuture = savedMatchFuture.thenCompose { savedMatch ->
            match.id = savedMatch.id
            val savedMatchPlayerFutures = match
                    .matchPlayers
                    .stream()
                    .map { matchPlayer -> matchPlayerRepository.save(matchPlayer) }
                    .collect<List<CompletableFuture<MatchPlayer>>, Any>(Collectors.toList())

            CompletableFuture.allOf(*savedMatchPlayerFutures.toTypedArray<CompletableFuture>())
        }

        return savedMatchFuture
                .thenCombine(savedMatchPlayersFuture) { savedMatch, nothing -> savedMatch }
                .thenCompose(Function<Match, CompletionStage<Match>> { this.populate(it) })
    }

    override fun all(): CompletableFuture<List<Match>> {
        return matchRepository.all().thenComposeAsync { matches ->
            val completableFutures = matches
                    .stream()
                    .map<CompletableFuture<Match>>(Function<Match, CompletableFuture<Match>> { this.populate(it) })
                    .collect<List<CompletableFuture<Match>>, Any>(Collectors.toList())

            val allFutures = CompletableFuture.allOf(
                    *completableFutures
                            .toTypedArray<CompletableFuture>()
            )
            allFutures.thenApplyAsync { future ->
                completableFutures
                        .stream()
                        .map<Match>(Function<CompletableFuture<Match>, Match> { it.join() })
                        .collect<List<Match>, Any>(Collectors.toList())
            }
        }
    }

    fun populate(match: Match): CompletableFuture<Match> {
        val fullyPopulatedMatch = AtomicReference<Match>()

        return matchPopulator.populate(match).thenApply { populatedMatch ->
            fullyPopulatedMatch.set(populatedMatch)

            populatedMatch
        }.thenCompose { populatedMatch ->
            val allFuturePopulatedMatchPlayers = populatedMatch
                    .matchPlayers
                    .stream()
                    .map { matchPlayer ->
                        matchPlayerPopulator.populate(matchPlayer).thenApply<Void> { populatedMatchPlayer ->
                            matchPlayer.user = populatedMatchPlayer.user
                            matchPlayer.match = populatedMatchPlayer.match
                            matchPlayer.role = populatedMatchPlayer.role
                            matchPlayer.team = populatedMatchPlayer.team
                            null
                        }
                    }.collect<List<CompletableFuture<Void>>, Any>(Collectors.toList())

            CompletableFuture.allOf(*allFuturePopulatedMatchPlayers.toTypedArray<CompletableFuture>())
        }.thenApply { nothing -> fullyPopulatedMatch.get() }
    }

    fun addListener(listener: MatchHandlerListener) {
        listeners.add(listener)
    }

    fun removeListener(listener: MatchHandlerListener) {
        listeners.remove(listener)
    }

    private fun notifyListenersOnMatchAdd(match: Match) {
        for (listener in listeners) {
            listener.onAddMatch(match)
        }
    }

    private fun notifyListenersOnMatchUpdate(match: Match) {
        for (listener in listeners) {
            listener.onUpdateMatch(match)
        }
    }

    private fun notifyListenersOnMatchRemove(match: Match) {
        for (listener in listeners) {
            listener.onRemoveMatch(match)
        }
    }

    override fun onCreate(match: Match) {
        notifyListenersOnMatchAdd(match)
    }

    override fun onUpdate(match: Match) {
        notifyListenersOnMatchUpdate(match)
    }

    override fun onDelete(match: Match) {
        notifyListenersOnMatchRemove(match)
    }

    private fun checkMatchValidity(match: Match) {
        kotlin.requireNotNull(match.game) { "Cannot create a match without a game" }

        kotlin.requireNotNull(match.game!!.id) { "Cannot create a match with a game that has no id" }

        kotlin.require(!match.matchPlayers.isEmpty()) { "Cannot create a match without any players" }

        for (player in match.matchPlayers) {
            kotlin.requireNotNull(player.match) { "Cannot create a match where a match player has no match" }

            kotlin.requireNotNull(player.user) { "Cannot create a match where a match player has no user" }

            kotlin.requireNotNull(player.user!!.id) { "Cannot create a match where a match player with a user that has no id" }

            kotlin.require(!(player.team == null && player.role != null)) { "Cannot create a match where a match player with a role but no team" }

            kotlin.require(!(player.team != null && player.team!!.id == null)) { "Cannot create a match where a match player with a team that has no id" }

            kotlin.require(!(player.role != null && player.role!!.id == null)) { "Cannot create a match where a match player with a role that has no id" }
        }
    }
}