package com.yahyeet.boardbook.model.mock.repository

import com.yahyeet.boardbook.model.entity.MatchPlayer
import com.yahyeet.boardbook.model.repository.IMatchPlayerRepository
import java.util.concurrent.CompletableFuture
import java.util.stream.Collectors

class MockMatchPlayerRepository : AbstractMockRepository<MatchPlayer>(), IMatchPlayerRepository {
    override fun findMatchPlayersByMatchId(id: String): CompletableFuture<List<MatchPlayer>> {
        return CompletableFuture.supplyAsync { mockDatabase.stream().filter { player -> player.match!!.id == id }.collect<List<MatchPlayer>, Any>(Collectors.toList()) }
    }

    override fun findMatchPlayersByUserId(id: String): CompletableFuture<List<MatchPlayer>> {
        return CompletableFuture.supplyAsync { mockDatabase.stream().filter { player -> player.user!!.id == id }.collect<List<MatchPlayer>, Any>(Collectors.toList()) }
    }
}
