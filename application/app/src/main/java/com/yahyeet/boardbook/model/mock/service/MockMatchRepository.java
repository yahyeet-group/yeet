package com.yahyeet.boardbook.model.mock.service;

import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.repository.IMatchRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class MockMatchRepository implements IMatchRepository {

	public List<Match> mockDatabase = new ArrayList<>();
	public int idCount = 0;
	public MockUserRepository userRepository;

	public MockMatchRepository(MockUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public CompletableFuture<Match> create(Match entity) {
		return CompletableFuture.supplyAsync(() -> {
			entity.setId(Integer.toString(idCount));
			idCount++;
			mockDatabase.add(entity);
			return entity;
		});
	}

	@Override
	public CompletableFuture<Match> find(String id) {
		return CompletableFuture.supplyAsync(() -> {

			for (Match u : mockDatabase) {
				if (u.getId().equals(id))
					return u;
			}

			throw new CompletionException(new Exception("No Match found"));
		});
	}

	@Override
	public CompletableFuture<Match> update(Match entity) {
		return CompletableFuture.supplyAsync(() -> {

			for (Match u : mockDatabase) {
				if (u.getId().equals(entity.getId())) {
					u = entity;
					return u;
				}
			}
			throw new CompletionException(new Exception("Not a Match in database"));
		});
	}

	@Override
	public CompletableFuture<Void> remove(Match entity) {
		return CompletableFuture.supplyAsync(() -> {
			for (int i = 0; i < mockDatabase.size(); i++) {
				if (entity.getId().equals(mockDatabase.get(i).getId())) {
					mockDatabase.remove(i);
					return null;
				}
			}

			throw new CompletionException(new Exception("Not a Match in database"));
		});
	}

	@Override
	public CompletableFuture<List<Match>> all() {
		return CompletableFuture.supplyAsync(() -> mockDatabase);
	}

	@Override
	public CompletableFuture<List<Match>> findMatchesByUserId(String id) {
		return CompletableFuture.supplyAsync(() -> {
			User user = null;

			for (User u : userRepository.mockDatabase) {
				if (u.getId().equals(id))
					user = u;
			}

			if (user != null) {
				return new ArrayList<>(user.getMatches());
			}

			throw new CompletionException(new Exception("Id is not a User in database"));
		});
	}


}
