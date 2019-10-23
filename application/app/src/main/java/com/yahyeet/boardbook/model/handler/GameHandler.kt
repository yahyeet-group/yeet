package com.yahyeet.boardbook.model.handler

import com.yahyeet.boardbook.model.entity.Game
import com.yahyeet.boardbook.model.entity.GameRole
import com.yahyeet.boardbook.model.entity.GameTeam
import com.yahyeet.boardbook.model.handler.populator.GamePopulator
import com.yahyeet.boardbook.model.handler.populator.GameTeamPopulator
import com.yahyeet.boardbook.model.repository.IGameRepository
import com.yahyeet.boardbook.model.repository.IGameRoleRepository
import com.yahyeet.boardbook.model.repository.IGameTeamRepository
import com.yahyeet.boardbook.model.repository.IMatchRepository
import com.yahyeet.boardbook.model.repository.IRepositoryListener

import java.util.ArrayList
import java.util.concurrent.CompletableFuture
import java.util.concurrent.atomic.AtomicReference
import java.util.stream.Collectors

class GameHandler(private val gameRepository: IGameRepository,
                  private val gameRoleRepository: IGameRoleRepository,
                  private val gameTeamRepository: IGameTeamRepository,
                  matchRepository: IMatchRepository) : IRepositoryListener<Game>, EntityHandler<Game> {
    private val listeners = ArrayList<GameHandlerListener>()

    private val gamePopulator: GamePopulator
    private val gameTeamPopulator: GameTeamPopulator

    init {

        gamePopulator = GamePopulator(matchRepository, gameTeamRepository)
        gameTeamPopulator = GameTeamPopulator(gameRepository, gameRoleRepository)
    }

    override fun find(id: String): CompletableFuture<Game> {
        return gameRepository.find(id).thenCompose(Function<Game, CompletionStage<Game>> { this.populate(it) })
    }

    override fun save(game: Game): CompletableFuture<Game> {
        checkGameValidity(game)

        val savedGameFuture = gameRepository.save(game)
        val savedGameTeamsAndRolesFuture = savedGameFuture.thenCompose { savedGame ->
            game.id = savedGame.id
            val savedGameTeamsFuture = game
                    .teams
                    .stream()
                    .map { team -> gameTeamRepository.save(team) }
                    .collect<List<CompletableFuture<GameTeam>>, Any>(Collectors.toList())

            val allOfSavedGameTeamsFuture = CompletableFuture.allOf(*savedGameTeamsFuture.toTypedArray<CompletableFuture>())
            allOfSavedGameTeamsFuture.thenApply { future -> savedGameTeamsFuture.stream().map<GameTeam>(Function<CompletableFuture<GameTeam>, GameTeam> { it.join() }).collect<List<GameTeam>, Any>(Collectors.toList()) }
        }.thenCompose { savedGameTeams ->
            for (index in 0 until game.teams.size) {
                game.teams[index].id = savedGameTeams[index].id
            }
            val allFutureSavedGameRoles = ArrayList<CompletableFuture<GameRole>>()

            game.teams.forEach { team ->
                team
                        .roles
                        .forEach { role -> allFutureSavedGameRoles.add(gameRoleRepository.save(role)) }
            }

            CompletableFuture.allOf(*allFutureSavedGameRoles.toTypedArray<CompletableFuture>())
        }

        return savedGameFuture
                .thenCombine(savedGameTeamsAndRolesFuture) { savedGame, nothing -> savedGame }
                .thenCompose(Function<Game, CompletionStage<Game>> { this.populate(it) })
    }

    override fun all(): CompletableFuture<List<Game>> {
        return gameRepository.all().thenComposeAsync { games ->
            val completableFutures = games
                    .stream()
                    .map<CompletableFuture<Game>>(Function<Game, CompletableFuture<Game>> { this.populate(it) })
                    .collect<List<CompletableFuture<Game>>, Any>(Collectors.toList())

            val allFutures = CompletableFuture.allOf(
                    *completableFutures
                            .toTypedArray<CompletableFuture>()
            )
            allFutures.thenApplyAsync { future ->
                completableFutures
                        .stream()
                        .map<Game>(Function<CompletableFuture<Game>, Game> { it.join() })
                        .collect<List<Game>, Any>(Collectors.toList())
            }
        }
    }

    fun populate(game: Game): CompletableFuture<Game> {
        val fullyPopulatedGame = AtomicReference<Game>()

        return gamePopulator.populate(game).thenApply<Any> { populatedGame ->
            fullyPopulatedGame.set(populatedGame)
            null
        }.thenCompose { nothing ->
            val allFuturePopulatedGameTeams = fullyPopulatedGame
                    .get()
                    .teams
                    .stream()
                    .map { team ->
                        gameTeamPopulator
                                .populate(team)
                                .thenApply<Void> { populatedTeam ->
                                    team.game = populatedTeam.game
                                    team.roles.clear()
                                    populatedTeam.roles.forEach(Consumer<GameRole> { team.addRole(it) })
                                    null
                                }
                    }
                    .collect<List<CompletableFuture<Void>>, Any>(Collectors.toList())

            CompletableFuture.allOf(*allFuturePopulatedGameTeams.toTypedArray<CompletableFuture>())
        }.thenApply { nothing -> fullyPopulatedGame.get() }
    }

    fun addListener(listener: GameHandlerListener) {
        listeners.add(listener)
    }

    fun removeListener(listener: GameHandlerListener) {
        listeners.remove(listener)
    }

    private fun notifyListenersOnGameAdd(game: Game) {
        for (listener in listeners) {
            listener.onAddGame(game)
        }
    }

    private fun notifyListenersOnGameUpdate(game: Game) {
        for (listener in listeners) {
            listener.onUpdateGame(game)
        }
    }

    private fun notifyListenersOnGameRemove(game: Game) {
        for (listener in listeners) {
            listener.onRemoveGame(game)
        }
    }

    override fun onCreate(game: Game) {
        notifyListenersOnGameAdd(game)
    }

    override fun onUpdate(game: Game) {
        notifyListenersOnGameUpdate(game)
    }

    override fun onDelete(game: Game) {
        notifyListenersOnGameRemove(game)
    }

    private fun checkGameValidity(game: Game) {
        if (game.teams != null) {
            for (team in game.teams) {
                kotlin.requireNotNull(team.game) { "Cannot create a team without a game" }
                if (team.roles != null) {
                    for (role in team.roles) {
                        kotlin.requireNotNull(role.team) { "Cannot create a role without a team" }
                    }
                }
            }
        }
    }
}
