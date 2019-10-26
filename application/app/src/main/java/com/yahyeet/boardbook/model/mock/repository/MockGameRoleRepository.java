package com.yahyeet.boardbook.model.mock.repository;

import com.yahyeet.boardbook.model.entity.GameRole;
import com.yahyeet.boardbook.model.repository.IGameRoleRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Mock implementation of a game role repository
 */
public class MockGameRoleRepository extends AbstractMockRepository<GameRole> implements IGameRoleRepository {
	@Override
	public CompletableFuture<List<GameRole>> findRolesByTeamId(String id) {
		return CompletableFuture.supplyAsync(() ->
			mockDatabase
				.stream()
				.filter(role -> role.getTeam().getId().equals(id))
				.collect(Collectors.toList())
		);
	}
}
