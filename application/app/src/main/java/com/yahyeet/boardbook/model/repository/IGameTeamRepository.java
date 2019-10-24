package com.yahyeet.boardbook.model.repository;

import com.yahyeet.boardbook.model.entity.GameTeam;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IGameTeamRepository extends IRepository<GameTeam> {
	/**
	 * Retrieves all game teams that belong to a game
	 *
	 * @param id Game id
	 * @return A completable future that resolves to a list of game teams
	 */
	CompletableFuture<List<GameTeam>> findTeamsByGameId(String id);
}
