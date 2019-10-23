package com.yahyeet.boardbook.model.mock.repository

import com.yahyeet.boardbook.model.entity.Match
import com.yahyeet.boardbook.model.entity.User
import com.yahyeet.boardbook.model.repository.IRepositoryListener
import com.yahyeet.boardbook.model.repository.IUserRepository

import java.util.ArrayList
import java.util.HashMap
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionException
import java.util.concurrent.atomic.AtomicBoolean
import java.util.function.Function

class MockUserRepository : AbstractMockRepository<User>(), IUserRepository {

    override fun findFriendsByUserId(id: String): CompletableFuture<List<User>> {
        return find(id).thenApply { user -> user.friends }
    }
}
