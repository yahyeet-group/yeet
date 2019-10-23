package com.yahyeet.boardbook.presenter

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.yahyeet.boardbook.activity.account.IAccountManagerActivity

import android.os.Looper

import com.yahyeet.boardbook.model.entity.Game
import com.yahyeet.boardbook.model.entity.User
import com.yahyeet.boardbook.model.util.EmailFailedException
import com.yahyeet.boardbook.model.util.PasswordFailedException

import java.util.ArrayList
import java.util.concurrent.CompletableFuture

class AccountManagerPresenter(private val accountManagerActivity: IAccountManagerActivity) {
    // Set true to skip login
    private val fastPass = false

    init {
        if (fastPass) {
            BoardbookSingleton.getInstance().authHandler.loggedInUser = User("The Almighty Temp User")
            finishAccountManager()
        }
    }

    @Throws(EmailFailedException::class, PasswordFailedException::class)
    fun loginAccount(email: String, password: String) {

        if (email.isEmpty()) {
            throw EmailFailedException("Email can not be empty.")
        } else if (emailInvalid(email)) {
            throw EmailFailedException("Email is not in a correct format")
        }

        if (passwordInvalid(password)) {
            throw PasswordFailedException("Password needs to be longer than 6 characters")
        }

        accountManagerActivity.disableManagerInteraction()
        BoardbookSingleton.getInstance().authHandler.login(email, password).thenAccept { u ->

            finishAccountManager()

        }.exceptionally { e ->

            e.printStackTrace()
            android.os.Handler(Looper.getMainLooper()).post {
                accountManagerActivity.enableManagerInteraction()
                //TODO: Weird getCause calls
                if (e.cause.cause is FirebaseAuthInvalidCredentialsException) {
                    accountManagerActivity.loginFailed(Exception("Incorrect email or password"))
                } else
                    accountManagerActivity.loginFailed(Exception("Account not found"))


            }
            null
        }

    }

    @Throws(EmailFailedException::class, PasswordFailedException::class)
    fun registerAccount(email: String, password: String, username: String) {

        if (email.isEmpty()) {
            throw EmailFailedException("Email can not be empty.")
        } else if (emailInvalid(email)) {
            throw EmailFailedException("Email is not in a correct format")
        }

        if (passwordInvalid(password)) {
            throw PasswordFailedException("Password needs to be longer than 6 characters")
        }


        accountManagerActivity.disableManagerInteraction()
        BoardbookSingleton.getInstance().authHandler.signup(email, password, username).thenAccept { u ->
            // access logged in user from "u"
            finishAccountManager()
        }.exceptionally { e ->
            // Handle error ("e")
            // TODO: Make presenter tell view to act upon different exceptions
            android.os.Handler(Looper.getMainLooper()).post {
                accountManagerActivity.enableManagerInteraction()
                if (e.cause.cause is FirebaseAuthUserCollisionException) {
                    accountManagerActivity.registerFailed(EmailFailedException(e.cause.cause.message))
                    //TODO: Hardcoded knowing it will go 2 ways deep, fix in repositories
                }

            }
            e.printStackTrace()
            null
        }
    }

    private fun finishAccountManager() {
        accountManagerActivity.finishAccountManager()
    }

    /**
     * Checks if email matches android pattern for valid email
     *
     * @param email gets compared against android email pattern
     * @return if email is valid or not
     */
    private fun emailInvalid(email: CharSequence): Boolean {
        return !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    /**
     * Checks if password is valid or not
     *
     * @param password to be validated
     * @return if password is valid or not
     */
    private fun passwordInvalid(password: CharSequence): Boolean {
        return password.length < 6
    }
}
