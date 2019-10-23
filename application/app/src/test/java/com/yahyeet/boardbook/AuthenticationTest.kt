package com.yahyeet.boardbook

import com.yahyeet.boardbook.model.entity.User
import com.yahyeet.boardbook.model.handler.AuthHandler
import com.yahyeet.boardbook.model.handler.UserHandler
import com.yahyeet.boardbook.model.mock.service.MockAuthService
import com.yahyeet.boardbook.model.mock.repository.MockMatchRepository
import com.yahyeet.boardbook.model.mock.repository.MockUserRepository

import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

import java.util.ArrayList
import java.util.concurrent.ExecutionException

class AuthenticationTest {
    internal var authenticationUsers: MutableList<MockAuthService.AuthenticationUser>? = null
    internal lateinit var mockAuthService: MockAuthService
    internal lateinit var authHandler: AuthHandler

    @Before
    fun initTest() {
        authenticationUsers = ArrayList()
        mockAuthService = MockAuthService(authenticationUsers as ArrayList<MockAuthService.AuthenticationUser>)
        authHandler = AuthHandler(mockAuthService)
    }

    @After
    fun cleanTest() {
        authenticationUsers = null
    }

    @Test
    fun login() {
        authenticationUsers!!.add(MockAuthService.AuthenticationUser("test@test.test", "MyPassword", User("Testificate")))

        try {
            authHandler.login("test@test.test", "MyPassword").thenAccept { user -> Assert.assertEquals("Testificate", user.name) }.get()
        } catch (e: ExecutionException) {
            Assert.fail()
        } catch (e: InterruptedException) {
            Assert.fail()
        }

        Assert.assertEquals("Testificate", authHandler.loggedInUser?.name)
    }

    @Test
    fun register() {
        try {
            authHandler.signup("prov@prov.prov", "MinKod", "Testare").thenAccept { user ->
                Assert.assertNotEquals(0, authenticationUsers!!.size.toLong())
                Assert.assertEquals("Testare", authenticationUsers!![0].user.name)
                Assert.assertEquals("MinKod", authenticationUsers!![0].password)
            }.get()
        } catch (e: ExecutionException) {
            Assert.fail()
        } catch (e: InterruptedException) {
            Assert.fail()
        }

    }

    @Test
    fun logout() {
        val authenticationUsers = ArrayList<MockAuthService.AuthenticationUser>()

        authHandler.loggedInUser = User()
        Assert.assertNotEquals(null, authHandler.loggedInUser)
        try {
            authHandler.logout().thenAccept { v -> Assert.assertEquals(null, authHandler.loggedInUser) }.get()
        } catch (e: ExecutionException) {
            Assert.fail()
        } catch (e: InterruptedException) {
            Assert.fail()
        }

    }
}
