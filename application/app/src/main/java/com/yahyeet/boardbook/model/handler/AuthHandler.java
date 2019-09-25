package com.yahyeet.boardbook.model.handler;

import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.service.IAuthService;

import java.util.concurrent.CompletableFuture;

public class AuthHandler {

    private IAuthService authService;
    private UserHandler userHandler;

    private User loggedInUser;

    public AuthHandler(IAuthService authService, UserHandler userHandler) {
        this.authService = authService;
        this.userHandler = userHandler;
    }

    public CompletableFuture<User> login(String email, String password) {
        return authService.login(email, password);
    }

    public CompletableFuture<Void> logout() {
        setLoggedInUser(null);
        return authService.logout();
    }

    public CompletableFuture<User> signup(String email, String password, String name) {
        return authService.signup(email, password, name).thenCompose(user -> userHandler.create(user));
    }


    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
}
