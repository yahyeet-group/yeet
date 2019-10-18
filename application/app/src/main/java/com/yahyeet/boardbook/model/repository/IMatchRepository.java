package com.yahyeet.boardbook.model.repository;

import com.yahyeet.boardbook.model.entity.Match;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IMatchRepository extends IRepository<Match> {
    CompletableFuture<List<Match>> findMatchesByGameId(String id);
}
