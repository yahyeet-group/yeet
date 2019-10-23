package com.yahyeet.boardbook.model.mock.service

import com.yahyeet.boardbook.model.entity.Match
import com.yahyeet.boardbook.model.entity.User
import com.yahyeet.boardbook.model.service.IAuthService

import java.util.ArrayList
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionException

class MockAuthService(private val mockUserDb: MutableList<AuthenticationUser>) : IAuthService {

    override fun login(email: String, password: String): CompletableFuture<User> {
        return CompletableFuture.supplyAsync {
            for (mockUser in mockUserDb) {
                if (mockUser.email == email && mockUser.password == password) {
                    return@CompletableFuture.supplyAsync mockUser . user
                }
            }

            throw CompletionException(Exception("User not found"))
        }
    }

    override fun logout(): CompletableFuture<Void> {
        return CompletableFuture.completedFuture(null)
    }

    override fun signup(email: String, password: String, name: String): CompletableFuture<User> {
        return CompletableFuture.supplyAsync {
            val user = User(name)
            val authUser = AuthenticationUser(email, password, user)
            mockUserDb.add(authUser)

            user
        }

    }

    class AuthenticationUser(var email: String, var password: String, var user: User) {

        val friends: List<User>
            get() = user.friends

        val matches: List<Match>
            get() = user.matches
    }
}
