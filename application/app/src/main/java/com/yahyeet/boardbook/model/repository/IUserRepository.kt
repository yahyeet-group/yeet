package com.yahyeet.boardbook.model.repository

import com.yahyeet.boardbook.model.entity.User
import java.util.concurrent.CompletableFuture

interface IUserRepository : IRepository<User> {
    fun findFriendsByUserId(id: String): CompletableFuture<List<User>>
}
