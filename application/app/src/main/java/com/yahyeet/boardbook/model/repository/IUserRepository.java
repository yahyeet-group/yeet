package com.yahyeet.boardbook.model.repository;

import com.yahyeet.boardbook.model.entity.User;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Interface that defines all the methods a user repository must implement
 */
public interface IUserRepository extends IRepository<User> {
    /**
     * Retrieves all users that are friends with a user
     *
     * @param id User id
     * @return A completable future that resolves to a list of users
     */
    CompletableFuture<List<User>> findFriendsByUserId(String id);
}
