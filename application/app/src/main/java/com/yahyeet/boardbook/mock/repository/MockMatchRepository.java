package com.yahyeet.boardbook.mock.repository;

import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.model.repository.IMatchRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Mock implementation of a match repository
 */
public class MockMatchRepository extends AbstractMockRepository<Match> implements IMatchRepository {
	@Override
	public CompletableFuture<List<Match>> findMatchesByGameId(String id) {
		return CompletableFuture.supplyAsync(() -> mockDatabase.stream().filter(match -> match.getGame().getId().equals(id)).collect(Collectors.toList()));
	}
}
