package com.yahyeet.boardbook.model.mock.repository;

import com.yahyeet.boardbook.model.entity.GameTeam;
import com.yahyeet.boardbook.model.repository.IGameTeamRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Mock implementation of a game team repository
 */
public class MockGameTeamRepository extends AbstractMockRepository<GameTeam> implements IGameTeamRepository {
	@Override
	public CompletableFuture<List<GameTeam>> findTeamsByGameId(String id) {
		return CompletableFuture.supplyAsync(() ->
			mockDatabase
				.stream()
				.filter(team -> team.getGame().getId().equals(id))
				.collect(Collectors.toList())
		);
	}
}
