package com.yahyeet.boardbook.model.firebase.service

import android.util.Log

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.yahyeet.boardbook.model.entity.User
import com.yahyeet.boardbook.model.handler.UserHandler
import com.yahyeet.boardbook.model.repository.IUserRepository
import com.yahyeet.boardbook.model.service.IAuthService

import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionException

class FirebaseAuthService(private val firebaseAuth: FirebaseAuth, private val userHandler: UserHandler) : IAuthService {

    override fun login(email: String, password: String): CompletableFuture<User> {
        return CompletableFuture.supplyAsync<String> {
            val task = firebaseAuth.signInWithEmailAndPassword(email, password)

            try {
                val result = Tasks.await(task)
                Log.d(TAG, "signInWithEmail:success")
                val firebaseUser = result.user
                assert(firebaseUser != null)

                return@CompletableFuture.supplyAsync firebaseUser !!. getUid ()
            } catch (e: Exception) {
                throw CompletionException(e)
            }
        }.thenCompose { uid -> userHandler.find(uid) }
    }

    override fun logout(): CompletableFuture<Void> {
        firebaseAuth.signOut()
        return CompletableFuture.completedFuture(null)
    }

    override fun signup(email: String, password: String, name: String): CompletableFuture<User> {
        return CompletableFuture.supplyAsync<User> {
            val task = firebaseAuth.createUserWithEmailAndPassword(email, password)

            try {
                val result = Tasks.await(task)
                Log.d(TAG, "createUserWithEmail:success")
                val firebaseUser = result.user
                assert(firebaseUser != null)
                val user = User(name)
                user.id = firebaseUser!!.uid
                return@CompletableFuture.supplyAsync user
            } catch (e: Exception) {
                throw CompletionException(e)
            }
        }.thenCompose(Function<User, CompletionStage<User>> { userHandler.save(it) })
    }

    companion object {

        private val TAG = "Authentication"
    }
}