package com.yahyeet.boardbook.model.repository;

import com.yahyeet.boardbook.model.entity.GameTeam;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IGameTeamRepository extends IRepository<GameTeam> {
	CompletableFuture<List<GameTeam>> findTeamsByGameId(String id);
}
