package com.yahyeet.boardbook.model.repository;

import com.yahyeet.boardbook.model.entity.Match;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IMatchRepository extends IRepository<Match> {
    /**
     * Retrieves all matches that belong to a game
     *
     * @param id Game id
     * @return A completable future that resolves to a list of matches
     */
    CompletableFuture<List<Match>> findMatchesByGameId(String id);
}
