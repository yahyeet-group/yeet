package com.yahyeet.boardbook.model.repository

import com.yahyeet.boardbook.model.entity.GameRole
import java.util.concurrent.CompletableFuture

interface IGameRoleRepository : IRepository<GameRole> {
    fun findRolesByTeamId(id: String): CompletableFuture<List<GameRole>>
}
