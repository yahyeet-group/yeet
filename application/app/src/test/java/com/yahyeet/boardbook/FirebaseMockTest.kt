package com.yahyeet.boardbook

import com.yahyeet.boardbook.model.Boardbook
import com.yahyeet.boardbook.model.entity.Game
import com.yahyeet.boardbook.model.entity.Match
import com.yahyeet.boardbook.model.entity.User
import com.yahyeet.boardbook.model.handler.AuthHandler
import com.yahyeet.boardbook.model.handler.GameHandler
import com.yahyeet.boardbook.model.handler.MatchHandler
import com.yahyeet.boardbook.model.handler.UserHandler
import com.yahyeet.boardbook.model.mock.repository.MockGameRoleRepository
import com.yahyeet.boardbook.model.mock.repository.MockGameTeamRepository
import com.yahyeet.boardbook.model.mock.repository.MockMatchPlayerRepository
import com.yahyeet.boardbook.model.mock.service.MockAuthService
import com.yahyeet.boardbook.model.mock.repository.MockGameRepository
import com.yahyeet.boardbook.model.mock.repository.MockMatchRepository
import com.yahyeet.boardbook.model.mock.repository.MockUserRepository

import org.junit.Before
import org.junit.Test

import java.util.ArrayList
import java.util.concurrent.ExecutionException

import org.junit.Assert.*

class FirebaseMockTest {


    internal lateinit var authService: MockAuthService

    internal lateinit var userRepository: MockUserRepository
    internal lateinit var gameRepository: MockGameRepository
    internal lateinit var matchRepository: MockMatchRepository
    internal lateinit var teamRepository: MockGameTeamRepository
    internal lateinit var playerRepository: MockMatchPlayerRepository
    internal lateinit var roleRepository: MockGameRoleRepository

    internal lateinit var boardbook: Boardbook


    @Before
    fun initTests() {
        authService = MockAuthService(ArrayList())

        userRepository = MockUserRepository()
        gameRepository = MockGameRepository()
        matchRepository = MockMatchRepository()
        teamRepository = MockGameTeamRepository()
        playerRepository = MockMatchPlayerRepository()
        roleRepository = MockGameRoleRepository()


        boardbook = Boardbook(
                AuthHandler(authService),
                UserHandler(userRepository, matchRepository, roleRepository, teamRepository, gameRepository, playerRepository),
                GameHandler(gameRepository, roleRepository, teamRepository, matchRepository),
                MatchHandler(matchRepository, playerRepository, gameRepository, roleRepository, teamRepository, userRepository)
        )

    }


    // This test works in its completableFutureness, write tests like this
    @Test
    @Throws(ExecutionException::class, InterruptedException::class)
    fun testLogin() {
        val user = User("TestUser")

        val friend = User("Friendly")
        val game = Game("Avalon", "Cool game", 1, 2, 3)
        val match = Match(game)

        user.friends.add(friend)
        user.matches.add(match)


        val authUser = MockAuthService.AuthenticationUser("email@email.test", "wordpass", user)

        authUser.user.name?.let {
            boardbook.authHandler.signup(authUser.email, authUser.password, it).thenAccept { user1 ->
            assertEquals(user.name, boardbook.authHandler.loggedInUser?.name)
            //TODO: Add more assert equals, id?
        }.get()
        }

    }

}
