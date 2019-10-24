package com.yahyeet.boardbook.model.repository;

import com.yahyeet.boardbook.model.entity.GameRole;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IGameRoleRepository extends IRepository<GameRole> {
	/**
	 * Retrieves all game roles that belong to a team
	 *
	 * @param id Team id
	 * @return A completable future that resolves to a list of game roles
	 */
	CompletableFuture<List<GameRole>> findRolesByTeamId(String id);
}
