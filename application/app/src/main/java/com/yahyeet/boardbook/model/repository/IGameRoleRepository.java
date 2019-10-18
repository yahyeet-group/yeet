package com.yahyeet.boardbook.model.repository;

import com.yahyeet.boardbook.model.entity.GameRole;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IGameRoleRepository extends IRepository<GameRole> {
	CompletableFuture<List<GameRole>> findRolesByTeamId(String id);
}
