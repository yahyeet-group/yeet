package com.yahyeet.boardbook.model.mock.repository;

import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.repository.IUserRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Mock implementation of a user repository
 */
public class MockUserRepository extends AbstractMockRepository<User> implements IUserRepository {
	@Override
	public CompletableFuture<List<User>> findFriendsByUserId(String id) {
		return find(id).thenApply(User::getFriends);
	}
}
