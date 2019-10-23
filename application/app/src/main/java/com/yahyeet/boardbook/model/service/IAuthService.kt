package com.yahyeet.boardbook.model.service

import com.yahyeet.boardbook.model.entity.User

import java.util.concurrent.CompletableFuture

interface IAuthService {
    fun login(email: String, password: String): CompletableFuture<User>

    fun logout(): CompletableFuture<Void>

    fun signup(email: String, password: String, name: String): CompletableFuture<User>
}
