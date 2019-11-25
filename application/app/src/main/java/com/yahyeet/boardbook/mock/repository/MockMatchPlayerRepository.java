package com.yahyeet.boardbook.mock.repository;

import com.yahyeet.boardbook.model.entity.MatchPlayer;
import com.yahyeet.boardbook.model.repository.IMatchPlayerRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Mock implementation of a match player repository
 */
public class MockMatchPlayerRepository extends AbstractMockRepository<MatchPlayer> implements IMatchPlayerRepository {
	@Override
	public CompletableFuture<List<MatchPlayer>> findMatchPlayersByMatchId(String id) {
		return CompletableFuture.supplyAsync(() ->
			mockDatabase
				.stream()
				.filter(player -> player.getMatch().getId().equals(id))
				.collect(Collectors.toList())
		);
	}

	@Override
	public CompletableFuture<List<MatchPlayer>> findMatchPlayersByUserId(String id) {
		return CompletableFuture.supplyAsync(() ->
			mockDatabase
				.stream()
				.filter(player -> player.getUser().getId().equals(id))
				.collect(Collectors.toList()));
	}
}
