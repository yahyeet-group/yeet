package com.yahyeet.boardbook.model.handler.populator

import com.yahyeet.boardbook.model.entity.Game
import com.yahyeet.boardbook.model.entity.Match
import com.yahyeet.boardbook.model.entity.MatchPlayer
import com.yahyeet.boardbook.model.repository.IGameRepository
import com.yahyeet.boardbook.model.repository.IMatchPlayerRepository
import java.util.concurrent.CompletableFuture

class MatchPopulator(private val gameRepository: IGameRepository, private val matchPlayerRepository: IMatchPlayerRepository) {

    fun populate(match: Match): CompletableFuture<Match> {
        val populatedMatch = Match(match.id)

        val futureGame = gameRepository.find(match.game!!.id)
        val futureMatchPlayers = matchPlayerRepository.findMatchPlayersByMatchId(populatedMatch.id)

        return futureGame.thenCombine(futureMatchPlayers) { game, matchPlayers ->
            populatedMatch.game = game
            matchPlayers.forEach(Consumer<MatchPlayer> { populatedMatch.addMatchPlayer(it) })

            populatedMatch
        }
    }
}
