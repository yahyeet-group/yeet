package com.yahyeet.boardbook.model.mock.repository

import com.yahyeet.boardbook.model.entity.Game
import com.yahyeet.boardbook.model.entity.Match
import com.yahyeet.boardbook.model.entity.User
import com.yahyeet.boardbook.model.repository.IMatchRepository
import com.yahyeet.boardbook.model.repository.IRepositoryListener

import java.lang.reflect.Array
import java.util.ArrayList
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionException
import java.util.concurrent.atomic.AtomicBoolean
import java.util.function.Function
import java.util.stream.Collectors

class MockMatchRepository : AbstractMockRepository<Match>(), IMatchRepository {

    override fun findMatchesByGameId(id: String): CompletableFuture<List<Match>> {
        return CompletableFuture.supplyAsync { mockDatabase.stream().filter { match -> match.game!!.id == id }.collect<List<Match>, Any>(Collectors.toList()) }
    }
}
