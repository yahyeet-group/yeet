package com.yahyeet.boardbook.model.handler

import com.yahyeet.boardbook.model.entity.Game
import com.yahyeet.boardbook.model.entity.Match
import com.yahyeet.boardbook.model.entity.User
import com.yahyeet.boardbook.model.handler.populator.MatchPlayerPopulator
import com.yahyeet.boardbook.model.handler.populator.MatchPopulator
import com.yahyeet.boardbook.model.handler.populator.UserPopulator
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

class UserHandler(private val userRepository: IUserRepository,
                  matchRepository: IMatchRepository,
                  gameRoleRepository: IGameRoleRepository,
                  gameTeamRepository: IGameTeamRepository,
                  gameRepository: IGameRepository,
                  matchPlayerRepository: IMatchPlayerRepository) : IRepositoryListener<User>, EntityHandler<User> {
    private val listeners = ArrayList<UserHandlerListener>()

    private val matchPlayerPopulator: MatchPlayerPopulator
    private val matchPopulator: MatchPopulator
    private val userPopulator: UserPopulator

    init {
        matchPlayerPopulator = MatchPlayerPopulator(gameRoleRepository, gameTeamRepository, matchRepository, userRepository)
        matchPopulator = MatchPopulator(gameRepository, matchPlayerRepository)
        userPopulator = UserPopulator(matchPlayerRepository, matchRepository, userRepository)
    }

    override fun find(id: String): CompletableFuture<User> {
        return userRepository.find(id).thenCompose(Function<User, CompletionStage<User>> { this.populate(it) })
    }

    override fun save(user: User): CompletableFuture<User> {
        checkUserValidity(user)

        return userRepository.save(user).thenCompose(Function<User, CompletionStage<User>> { this.populate(it) })
    }

    override fun all(): CompletableFuture<List<User>> {
        return userRepository.all().thenComposeAsync { users ->
            val completableFutures = users
                    .stream()
                    .map<CompletableFuture<User>>(Function<User, CompletableFuture<User>> { this.populate(it) })
                    .collect<List<CompletableFuture<User>>, Any>(Collectors.toList())

            val allFutures = CompletableFuture.allOf(
                    *completableFutures
                            .toTypedArray<CompletableFuture>()
            )
            allFutures.thenApplyAsync { future ->
                completableFutures
                        .stream()
                        .map<User>(Function<CompletableFuture<User>, User> { it.join() })
                        .collect<List<User>, Any>(Collectors.toList())
            }
        }
    }

    fun populate(user: User): CompletableFuture<User> {
        val fullyPopulatedUser = AtomicReference<User>()

        return userPopulator.populate(user).thenApply<Any> { populatedUser ->
            fullyPopulatedUser.set(populatedUser)
            null
        }.thenCompose { nothing ->
            val allFuturePopulatedMatches = fullyPopulatedUser
                    .get()
                    .matches
                    .stream().map { match -> matchPopulator.populate(match) }
                    .collect<List<CompletableFuture<Match>>, Any>(Collectors.toList())

            val allOfFutureFuturePopulatedMatches = CompletableFuture.allOf(*allFuturePopulatedMatches.toTypedArray<CompletableFuture>())

            allOfFutureFuturePopulatedMatches.thenApply { future ->
                allFuturePopulatedMatches
                        .stream()
                        .map<Match>(Function<CompletableFuture<Match>, Match> { it.join() })
                        .collect<List<Match>, Any>(Collectors.toList())
            }
        }.thenApply { populatedMatches ->
            fullyPopulatedUser.get().matches.clear()
            populatedMatches.forEach { populatedMatch -> fullyPopulatedUser.get().addMatch(populatedMatch) }

            populatedMatches
        }.thenCompose { populatedMatches ->
            val allFuturePopulatedMatchPlayers = ArrayList<CompletableFuture<Void>>()

            fullyPopulatedUser
                    .get()
                    .matches
                    .forEach { match ->
                        match
                                .matchPlayers
                                .forEach { matchPlayer ->
                                    allFuturePopulatedMatchPlayers.add(matchPlayerPopulator.populate(matchPlayer).thenApply { populatedMatchPlayer ->
                                        matchPlayer.user = populatedMatchPlayer.user
                                        matchPlayer.match = populatedMatchPlayer.match
                                        matchPlayer.role = populatedMatchPlayer.role
                                        null
                                    })
                                }
                    }

            CompletableFuture.allOf(*allFuturePopulatedMatchPlayers.toTypedArray<CompletableFuture>())
        }.thenApply { nothing -> fullyPopulatedUser.get() }
    }

    fun addListener(listener: UserHandlerListener) {
        listeners.add(listener)
    }

    fun removeListener(listener: UserHandlerListener) {
        listeners.remove(listener)
    }

    private fun notifyListenersOnUserAdd(user: User) {
        for (listener in listeners) {
            listener.onAddUser(user)
        }
    }

    private fun notifyListenersOnUserUpdate(user: User) {
        for (listener in listeners) {
            listener.onUpdateUser(user)
        }
    }

    private fun notifyListenersOnUserRemove(user: User) {
        for (listener in listeners) {
            listener.onRemoveUser(user)
        }
    }

    override fun onCreate(user: User) {
        notifyListenersOnUserAdd(user)
    }

    override fun onUpdate(user: User) {
        notifyListenersOnUserUpdate(user)
    }

    override fun onDelete(user: User) {
        notifyListenersOnUserRemove(user)
    }

    private fun checkUserValidity(user: User) {
        for (friend in user.friends) {
            kotlin.requireNotNull(friend.id) { "Cannot add friends that have no id" }
        }
    }
}
