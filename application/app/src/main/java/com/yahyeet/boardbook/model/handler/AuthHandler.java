package com.yahyeet.boardbook.model.handler;

import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.service.IAuthService;

import java.util.concurrent.CompletableFuture;

public class AuthHandler {

	private IAuthService authService;

	private User loggedInUser;

	public AuthHandler(IAuthService authService) {
		this.authService = authService;
	}

	public CompletableFuture<User> login(String email, String password) {
		return authService.login(email, password).thenApply(u -> {
			loggedInUser = u;

			return u;
		});
	}

	public CompletableFuture<Void> logout() {
		setLoggedInUser(null);
		return authService.logout();
	}

	public CompletableFuture<User> signup(String email, String password, String name) {
		return authService.signup(email, password, name).thenApply(u -> {
			loggedInUser = u;

			return u;
		});
	}

	public User getLoggedInUser() {
		return loggedInUser;
	}

	public void setLoggedInUser(User loggedInUser) {
		this.loggedInUser = loggedInUser;
	}
}
