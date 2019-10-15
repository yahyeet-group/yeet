package com.yahyeet.boardbook.model.mock.service;

import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.repository.IUserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class MockUserRepository implements IUserRepository {


	public List<User> mockDatabase = new ArrayList<>();
	public int idCount = 0;

	@Override
	public CompletableFuture<User> create(User entity) {
		return CompletableFuture.supplyAsync(() -> {
			entity.setId(Integer.toString(idCount));
			idCount++;

			if(entity.getFriends() == null)
				entity.setFriends(new ArrayList<>());

			if(entity.getMatches() == null)
				entity.setMatches(new ArrayList<>());

			mockDatabase.add(entity);
			return entity;
		});
	}

	@Override
	public CompletableFuture<User> find(String id) {
		return CompletableFuture.supplyAsync(() -> {

			for (User u : mockDatabase) {
				if (u.getId().equals(id))
					return u;
			}

			throw new CompletionException(new Exception("No user found"));
		});
	}

	@Override
	public CompletableFuture<User> update(User entity) {
		return CompletableFuture.supplyAsync(() -> {

			for (User u : mockDatabase) {
				if (u.getId().equals(entity.getId())) {
					u = entity;
					return u;
				}
			}
			throw new CompletionException(new Exception("Not a user in database"));
		});
	}

	@Override
	public CompletableFuture<Void> remove(User entity) {
		return CompletableFuture.supplyAsync(() -> {
			for (int i = 0; i < mockDatabase.size(); i++) {
				if (entity.getId().equals(mockDatabase.get(i).getId())) {
					mockDatabase.remove(i);
					return null;
				}
			}

			throw new CompletionException(new Exception("Not a user in database"));
		});
	}

	@Override
	public CompletableFuture<List<User>> all() {
		return CompletableFuture.supplyAsync(() -> mockDatabase);
	}

	@Override
	public CompletableFuture<List<User>> findFriendsByUserId(String id) {
		return CompletableFuture.supplyAsync(() -> {
			User user = null;

			for (User u : mockDatabase) {
				if (u.getId().equals(id))
					user = u;
			}

			if (user != null) {
				return new ArrayList<>(user.getFriends());
			}

			throw new CompletionException(new Exception("Id is not a user in database"));
		});
	}
}
