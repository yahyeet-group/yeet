package com.yahyeet.boardbook.model.handler;

import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.service.IAuthService;

import java.util.concurrent.CompletableFuture;

public class AuthHandler {

    private IAuthService authService;
    private UserHandler userHandler;

    public AuthHandler(IAuthService authService, UserHandler userHandler) {
        this.authService = authService;
        this.userHandler = userHandler;
    }

    CompletableFuture<User> login(String email, String password) {
        return authService.login(email, password);
    }

    CompletableFuture<Void> logout() {
        return authService.logout();
    }

    CompletableFuture<User> signup(String email, String password, String name) {
        return authService.signup(email, password, name).thenCompose(user -> userHandler.create(user));
    }
}
