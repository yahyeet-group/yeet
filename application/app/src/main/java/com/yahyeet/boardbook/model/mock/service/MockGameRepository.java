package com.yahyeet.boardbook.model.mock.service;

import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.model.repository.IGameRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class MockGameRepository implements IGameRepository {


	public List<Game> mockDatabase = new ArrayList<>();
	public int idCount = 0;

	@Override
	public CompletableFuture<Game> create(Game entity) {
		return CompletableFuture.supplyAsync(() -> {
			entity.setId(Integer.toString(idCount));
			idCount++;
			mockDatabase.add(entity);
			return entity;
		});
	}

	@Override
	public CompletableFuture<Game> find(String id) {
		return CompletableFuture.supplyAsync(() -> {

			for (Game u : mockDatabase) {
				if (u.getId().equals(id))
					return u;
			}

			throw new CompletionException(new Exception("No Game found"));
		});
	}

	@Override
	public CompletableFuture<Game> update(Game entity) {
		return CompletableFuture.supplyAsync(() -> {

			for (Game u : mockDatabase) {
				if (u.getId().equals(entity.getId())) {
					u = entity;
					return u;
				}
			}
			throw new CompletionException(new Exception("Not a Game in database"));
		});
	}

	@Override
	public CompletableFuture<Void> remove(Game entity) {
		return CompletableFuture.supplyAsync(() -> {
			for (int i = 0; i < mockDatabase.size(); i++) {
				if (entity.getId().equals(mockDatabase.get(i).getId())) {
					mockDatabase.remove(i);
					return null;
				}
			}

			throw new CompletionException(new Exception("Not a Game in database"));
		});
	}

	@Override
	public CompletableFuture<List<Game>> all() {
		return CompletableFuture.supplyAsync(() -> mockDatabase);
	}
}
