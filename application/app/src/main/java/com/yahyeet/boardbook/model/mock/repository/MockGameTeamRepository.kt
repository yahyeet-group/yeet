package com.yahyeet.boardbook.model.mock.repository

import com.yahyeet.boardbook.model.entity.GameTeam
import com.yahyeet.boardbook.model.repository.IGameTeamRepository
import java.util.concurrent.CompletableFuture
import java.util.stream.Collectors

class MockGameTeamRepository : AbstractMockRepository<GameTeam>(), IGameTeamRepository {
    override fun findTeamsByGameId(id: String): CompletableFuture<List<GameTeam>> {
        return CompletableFuture.supplyAsync { mockDatabase.stream().filter { team -> team.game!!.id == id }.collect<List<GameTeam>, Any>(Collectors.toList()) }
    }
}
