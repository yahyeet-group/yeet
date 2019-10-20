package com.yahyeet.boardbook.model.mock.repository;

import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.repository.IMatchRepository;
import com.yahyeet.boardbook.model.repository.IRepositoryListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MockMatchRepository extends AbstractMockRepository<Match> implements IMatchRepository {

	@Override
	public CompletableFuture<List<Match>> findMatchesByGameId(String id) {
		return CompletableFuture.supplyAsync(() -> mockDatabase.stream().filter(match -> match.getGame().getId().equals(id)).collect(Collectors.toList()));
	}
}
