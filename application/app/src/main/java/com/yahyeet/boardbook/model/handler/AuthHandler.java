package com.yahyeet.boardbook.model.handler;

import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.service.IAuthService;

import java.util.concurrent.CompletableFuture;

/**
 * Handles user authentication
 */
public class AuthHandler {
	private IAuthService authService;

	private User loggedInUser;

	public AuthHandler(IAuthService authService) {
		this.authService = authService;
	}

	/**
	 * Logs in a user via email and password
	 *
	 * @param email The email
	 * @param password The password
	 * @return A completable future that resolves to an user on a successful login attempt, else
	 * it throws an error
	 */
	public CompletableFuture<User> login(String email, String password) {
		return authService.login(email, password).thenApply(u -> {
			loggedInUser = u;

			return u;
		});
	}

	/**
	 * Logs out the currently logged in user
	 *
	 * @return A completable future than when resolves denotes that the logout action was completed
	 */
	public CompletableFuture<Void> logout() {
		setLoggedInUser(null);
		return authService.logout();
	}

	/**
	 * Registers a new user
	 *
	 * @param email The email
	 * @param password The password
	 * @param name The name
	 * @return A completable future than on a successful signup request returns the new user, else
	 * it throws an exception
	 */
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
