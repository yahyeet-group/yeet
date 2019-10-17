package com.yahyeet.boardbook.model.repository;

import com.yahyeet.boardbook.model.entity.MatchPlayer;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IMatchPlayerRepository extends IRepository<MatchPlayer>{
	CompletableFuture<List<MatchPlayer>> findMatchPlayersByMatchId(String id);
	CompletableFuture<List<MatchPlayer>> findMatchPlayersByUserId(String id);
}
