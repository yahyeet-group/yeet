package com.yahyeet.boardbook.model.handler.populator

import com.yahyeet.boardbook.model.entity.Match
import com.yahyeet.boardbook.model.entity.MatchPlayer
import com.yahyeet.boardbook.model.entity.User
import com.yahyeet.boardbook.model.repository.IMatchPlayerRepository
import com.yahyeet.boardbook.model.repository.IMatchRepository
import com.yahyeet.boardbook.model.repository.IUserRepository
import java.util.concurrent.CompletableFuture
import java.util.stream.Collectors

class UserPopulator(private val matchPlayerRepository: IMatchPlayerRepository, private val matchRepository: IMatchRepository, private val userRepository: IUserRepository) {

    fun populate(user: User): CompletableFuture<User> {
        val populatedUser = User(user.name)
        populatedUser.id = user.id

        val friendsFuture = userRepository.findFriendsByUserId(populatedUser.id)

        val futureMatchPlayers = matchPlayerRepository.findMatchPlayersByUserId(populatedUser.id)

        val futureMatches = futureMatchPlayers.thenCompose { matchPlayers ->
            val allMatchFutures = matchPlayers
                    .stream()
                    .map { matchPlayer -> matchRepository.find(matchPlayer.match!!.id) }
                    .collect<List<CompletableFuture<Match>>, Any>(Collectors.toList())

            val allOfFutureAllMatchFutures = CompletableFuture.allOf(*allMatchFutures.toTypedArray<CompletableFuture>())
            allOfFutureAllMatchFutures.thenApply { future ->
                allMatchFutures
                        .stream()
                        .map<Match>(Function<CompletableFuture<Match>, Match> { it.join() })
                        .collect<List<Match>, Any>(Collectors.toList())
            }
        }

        return friendsFuture.thenCombine(futureMatches) { friends, matches ->
            friends.forEach(Consumer<User> { populatedUser.addFriend(it) })
            matches.forEach(Consumer<Match> { populatedUser.addMatch(it) })
            populatedUser
        }
    }
}
