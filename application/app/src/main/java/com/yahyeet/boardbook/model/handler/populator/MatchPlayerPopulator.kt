package com.yahyeet.boardbook.model.handler.populator

import com.yahyeet.boardbook.model.entity.GameRole
import com.yahyeet.boardbook.model.entity.GameTeam
import com.yahyeet.boardbook.model.entity.Match
import com.yahyeet.boardbook.model.entity.MatchPlayer
import com.yahyeet.boardbook.model.entity.User
import com.yahyeet.boardbook.model.repository.IGameRoleRepository
import com.yahyeet.boardbook.model.repository.IGameTeamRepository
import com.yahyeet.boardbook.model.repository.IMatchRepository
import com.yahyeet.boardbook.model.repository.IUserRepository

import java.util.concurrent.CompletableFuture

class MatchPlayerPopulator(private val gameRoleRepository: IGameRoleRepository, private val gameTeamRepository: IGameTeamRepository, private val matchRepository: IMatchRepository, private val userRepository: IUserRepository) {

    fun populate(matchPlayer: MatchPlayer): CompletableFuture<MatchPlayer> {
        val populatedMatchPlayer = MatchPlayer(null, null, null, matchPlayer.win)
        populatedMatchPlayer.id = matchPlayer.id

        val futureGameRole: CompletableFuture<GameRole>
        if (matchPlayer.role == null) {
            futureGameRole = CompletableFuture.completedFuture(null)
        } else {
            futureGameRole = gameRoleRepository.find(matchPlayer.role!!.id)
        }
        val futureGameTeam: CompletableFuture<GameTeam>
        if (matchPlayer.team == null) {
            futureGameTeam = CompletableFuture.completedFuture(null)
        } else {
            futureGameTeam = gameTeamRepository.find(matchPlayer.team!!.id)
        }
        val futureMatch = matchRepository.find(matchPlayer.match!!.id)
        val futureUser = userRepository.find(matchPlayer.user!!.id)

        return futureGameRole.thenCombine<Match, Any>(futureMatch) { gameRole, match ->
            populatedMatchPlayer.role = gameRole
            populatedMatchPlayer.match = match
            null
        }.thenCombine<User, Any>(futureUser) { nothing, user ->
            populatedMatchPlayer.user = user
            null
        }.thenCombine(futureGameTeam) { nothing, gameTeam ->
            populatedMatchPlayer.team = gameTeam

            populatedMatchPlayer
        }
    }
}
