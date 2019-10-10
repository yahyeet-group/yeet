package com.yahyeet.boardbook.model.repository;

import com.yahyeet.boardbook.model.entity.User;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IUserRepository extends IRepository<User> {
    CompletableFuture<List<User>> findFriendsByUserId(String id);

    CompletableFuture<Void> addFriend(User currentUser, User targetUser);

    CompletableFuture<Void> removeFriend(User currentUser, User targetUser);
}
