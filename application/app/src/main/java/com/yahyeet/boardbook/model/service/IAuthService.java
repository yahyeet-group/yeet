package com.yahyeet.boardbook.model.service;

import com.yahyeet.boardbook.model.entity.User;

import java.util.concurrent.CompletableFuture;

/**
 * Interface that defines all the methods an authentication service must implement
 */
public interface IAuthService {
    /**
     * Logs in a user via email and password
     *
     * @param email The email
     * @param password The password
     * @return A completable future that resolves to an user on a successful login attempt, else
     * it throws an error
     */
    CompletableFuture<User> login(String email, String password);

    /**
     * Logs out the currently logged in user
     *
     * @return A completable future than when resolves denotes that the logout action was completed
     */
    CompletableFuture<Void> logout();

    /**
     * Registers a new user
     *
     * @param email The email
     * @param password The password
     * @param name The name
     * @return A completable future than on a successful signup request returns the new user, else
     * it throws an exception
     */
    CompletableFuture<User> signup(String email, String password, String name);
}
