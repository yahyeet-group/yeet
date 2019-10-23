package com.yahyeet.boardbook.model

import com.yahyeet.boardbook.model.entity.Game
import com.yahyeet.boardbook.model.entity.GameRole
import com.yahyeet.boardbook.model.entity.GameTeam
import com.yahyeet.boardbook.model.entity.Match
import com.yahyeet.boardbook.model.entity.MatchPlayer
import com.yahyeet.boardbook.model.entity.User
import com.yahyeet.boardbook.model.handler.GameHandler
import com.yahyeet.boardbook.model.handler.MatchHandler
import com.yahyeet.boardbook.model.handler.UserHandler
import com.yahyeet.boardbook.model.mock.repository.MockGameRepository
import com.yahyeet.boardbook.model.mock.repository.MockGameRoleRepository
import com.yahyeet.boardbook.model.mock.repository.MockGameTeamRepository
import com.yahyeet.boardbook.model.mock.repository.MockMatchPlayerRepository
import com.yahyeet.boardbook.model.mock.repository.MockMatchRepository
import com.yahyeet.boardbook.model.mock.repository.MockUserRepository

import org.junit.Before
import org.junit.Test

import java.util.concurrent.ExecutionException

import org.junit.Assert.assertEquals
import org.junit.Assert.fail

class UserHandlerTest {

    private var userHandler: UserHandler? = null
    private var matchHandler: MatchHandler? = null
    private var gameHandler: GameHandler? = null


    private var userRepository: MockUserRepository? = null
    private var matchRepository: MockMatchRepository? = null
    private var roleRepository: MockGameRoleRepository? = null
    private var teamRepository: MockGameTeamRepository? = null
    private var gameRepository: MockGameRepository? = null
    private var playerRepository: MockMatchPlayerRepository? = null

    @Before
    fun initTests() {
        userRepository = MockUserRepository()
        matchRepository = MockMatchRepository()
        roleRepository = MockGameRoleRepository()
        teamRepository = MockGameTeamRepository()
        gameRepository = MockGameRepository()
        playerRepository = MockMatchPlayerRepository()

        userHandler = UserHandler(
                userRepository,
                matchRepository,
                roleRepository,
                teamRepository,
                gameRepository,
                playerRepository
        )

        matchHandler = MatchHandler(
                matchRepository,
                playerRepository,
                gameRepository,
                roleRepository,
                teamRepository,
                userRepository
        )

        gameHandler = GameHandler(
                gameRepository,
                roleRepository,
                teamRepository,
                matchRepository
        )
    }

    @Test
    fun saveUserAndGet() {
        val user = User("Carl")
        try {
            val id = userHandler!!.save(user).get().id
            val result = userHandler!!.find(id).get()
            assertEquals(user, result)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } catch (e: ExecutionException) {
            e.printStackTrace()
        }

    }

    @Test
    fun getAllTest() {
        val user1 = User("Carlos")
        val user2 = User("Alexanderos")
        val user3 = User("Aaronos")
        val user4 = User("Dinos")
        val user5 = User("Rasmusos")

        try {
            userHandler!!.save(user1).get()
            userHandler!!.save(user2).get()
            userHandler!!.save(user3).get()
            userHandler!!.save(user4).get()
            userHandler!!.save(user5).get()
            val size = userHandler!!.all().get().size
            assertEquals(5, size.toLong())
        } catch (e: InterruptedException) {
            e.printStackTrace()
            fail()
        } catch (e: ExecutionException) {
            e.printStackTrace()
            fail()
        }

    }

    @Test(expected = IllegalArgumentException::class)
    fun saveUserWithFriendAndGet() {
        val user1 = User("Carl")
        val user2 = User("Rasmus")
        user1.addFriend(user2)
        try {
            userHandler!!.save(user1).get()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } catch (e: ExecutionException) {
            e.printStackTrace()
        }

    }

    @Test
    fun saveUserWithMatchPlayerAndGet() {
        var game = Game(
                "Avalon",
                "Cool game where you laugh when someone accuses you of being Oberon",
                4,
                5,
                10
        )
        val gt = GameTeam("Team name")
        gt.addRole(GameRole("Role name"))

        game.addTeam(gt)
        try {
            game = gameHandler!!.save(game).get()
        } catch (e: ExecutionException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        val match = Match(game)
        var user = User()
        try {
            user = userHandler!!.save(user).get()
        } catch (e: ExecutionException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        val gameTeam = game.teams[0]
        val gameRole = game.teams[0].roles[0]


        val player = MatchPlayer(user, gameRole, gameTeam, false)

        try {

            match.addMatchPlayer(player)
            matchHandler!!.save(match).get()
            val id = userHandler!!.save(user).get().id
            val result = userHandler!!.find(user.id).get()
            assertEquals(user, result)
        } catch (e: ExecutionException) {
            e.printStackTrace()
            fail()
        } catch (e: InterruptedException) {
            e.printStackTrace()
            fail()
        }

    }
}