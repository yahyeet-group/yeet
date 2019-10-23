package com.yahyeet.boardbook.model.handler

import com.yahyeet.boardbook.model.entity.User
import com.yahyeet.boardbook.model.service.IAuthService

import java.util.concurrent.CompletableFuture

class AuthHandler(private val authService: IAuthService) {

    var loggedInUser: User? = null

    fun login(email: String, password: String): CompletableFuture<User> {
        return authService.login(email, password).thenApply { u ->
            loggedInUser = u

            u
        }
    }

    fun logout(): CompletableFuture<Void> {
        loggedInUser = null
        return authService.logout()
    }

    fun signup(email: String, password: String, name: String): CompletableFuture<User> {
        return authService.signup(email, password, name).thenApply { u ->
            loggedInUser = u

            u
        }
    }
}
