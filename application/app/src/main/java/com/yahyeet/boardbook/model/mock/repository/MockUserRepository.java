package com.yahyeet.boardbook.model.mock.repository;

import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.repository.IUserRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MockUserRepository implements IUserRepository {

    @Override
    public CompletableFuture<User> create(User entity) {
        return null;
    }

    @Override
    public CompletableFuture<User> find(String id) {
        return null;
    }

    @Override
    public CompletableFuture<User> update(User entity) {
        return null;
    }

    @Override
    public CompletableFuture<Void> remove(User entity) {
        return null;
    }

    @Override
    public CompletableFuture<List<User>> all() {
        return null;
    }
}
