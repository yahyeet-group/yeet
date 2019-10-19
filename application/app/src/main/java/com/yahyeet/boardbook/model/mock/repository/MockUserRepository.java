package com.yahyeet.boardbook.model.mock.repository;

import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.repository.IRepositoryListener;
import com.yahyeet.boardbook.model.repository.IUserRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

public class MockUserRepository extends AbstractMockRepository<User> implements IUserRepository {

	@Override
	public CompletableFuture<List<User>> findFriendsByUserId(String id) {
		return find(id).thenApply(user -> user.getFriends());
	}
}
