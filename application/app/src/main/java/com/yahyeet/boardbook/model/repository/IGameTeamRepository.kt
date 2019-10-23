package com.yahyeet.boardbook.model.repository

import com.yahyeet.boardbook.model.entity.GameTeam
import java.util.concurrent.CompletableFuture

interface IGameTeamRepository : IRepository<GameTeam> {
    fun findTeamsByGameId(id: String): CompletableFuture<List<GameTeam>>
}
