package com.yahyeet.boardbook.model.service;

import com.yahyeet.boardbook.model.entity.User;

import java.util.concurrent.CompletableFuture;

public interface IAuthService {
    CompletableFuture<User> login(String email, String password);

    CompletableFuture<Void> logout();

    CompletableFuture<User> signup(String email, String password, String name);
}
