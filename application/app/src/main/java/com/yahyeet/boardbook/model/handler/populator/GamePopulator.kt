package com.yahyeet.boardbook.model.handler.populator

import com.yahyeet.boardbook.model.entity.Game
import com.yahyeet.boardbook.model.entity.GameTeam
import com.yahyeet.boardbook.model.entity.Match
import com.yahyeet.boardbook.model.repository.IGameTeamRepository
import com.yahyeet.boardbook.model.repository.IMatchRepository
import java.util.concurrent.CompletableFuture

class GamePopulator(private val matchRepository: IMatchRepository, private val gameTeamRepository: IGameTeamRepository) {

    fun populate(game: Game): CompletableFuture<Game> {
        val populatedGame = Game(
                game.name,
                game.description,
                game.difficulty,
                game.minPlayers,
                game.maxPlayers
        )
        populatedGame.id = game.id

        val futureMatches = matchRepository.findMatchesByGameId(game.id)
        val futureTeams = gameTeamRepository.findTeamsByGameId(game.id)

        return futureMatches.thenCombine(futureTeams) { matches, teams ->
            matches.forEach(Consumer<Match> { populatedGame.addMatch(it) })
            teams.forEach(Consumer<GameTeam> { populatedGame.addTeam(it) })

            populatedGame
        }
    }
}
