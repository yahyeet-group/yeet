package com.yahyeet.boardbook.model.handler.populator

import com.yahyeet.boardbook.model.entity.Game
import com.yahyeet.boardbook.model.entity.GameRole
import com.yahyeet.boardbook.model.entity.GameTeam
import com.yahyeet.boardbook.model.repository.IGameRepository
import com.yahyeet.boardbook.model.repository.IGameRoleRepository
import java.util.concurrent.CompletableFuture

class GameTeamPopulator(private val gameRepository: IGameRepository, private val gameRoleRepository: IGameRoleRepository) {

    fun populate(gameTeam: GameTeam): CompletableFuture<GameTeam> {
        val populatedGameTeam = GameTeam(gameTeam.name)
        populatedGameTeam.id = gameTeam.id

        val futureGame = gameRepository.find(gameTeam.game!!.id)
        val futureGameRoles = gameRoleRepository.findRolesByTeamId(populatedGameTeam.id)

        return futureGame.thenCombine(futureGameRoles) { game, roles ->
            populatedGameTeam.game = game
            roles.forEach(Consumer<GameRole> { populatedGameTeam.addRole(it) })

            populatedGameTeam
        }
    }
}
