package com.yahyeet.boardbook.model.mock.repository

import com.yahyeet.boardbook.model.entity.GameRole
import com.yahyeet.boardbook.model.repository.IGameRoleRepository
import java.util.concurrent.CompletableFuture
import java.util.stream.Collectors

class MockGameRoleRepository : AbstractMockRepository<GameRole>(), IGameRoleRepository {
    override fun findRolesByTeamId(id: String): CompletableFuture<List<GameRole>> {
        return CompletableFuture.supplyAsync { mockDatabase.stream().filter { role -> role.team!!.id == id }.collect<List<GameRole>, Any>(Collectors.toList()) }
    }
}
