package com.yahyeet.boardbook.model.repository

import com.yahyeet.boardbook.model.entity.Match
import java.util.concurrent.CompletableFuture

interface IMatchRepository : IRepository<Match> {
    fun findMatchesByGameId(id: String): CompletableFuture<List<Match>>
}
