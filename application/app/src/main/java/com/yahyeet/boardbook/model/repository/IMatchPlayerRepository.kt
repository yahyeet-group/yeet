package com.yahyeet.boardbook.model.repository

import com.yahyeet.boardbook.model.entity.MatchPlayer
import java.util.concurrent.CompletableFuture

interface IMatchPlayerRepository : IRepository<MatchPlayer> {
    fun findMatchPlayersByMatchId(id: String): CompletableFuture<List<MatchPlayer>>
    fun findMatchPlayersByUserId(id: String): CompletableFuture<List<MatchPlayer>>
}
