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

import org.junit.Assert
import org.junit.Before
import org.junit.Test

import java.util.concurrent.ExecutionException

import org.junit.Assert.assertEquals
import org.junit.Assert.fail

class MatchHandlerTest {

    private var matchHandler: MatchHandler? = null
    private var userHandler: UserHandler? = null
    private var gameHandler: GameHandler? = null

    private var matchRepository: MockMatchRepository? = null
    private var playerRepository: MockMatchPlayerRepository? = null
    private var gameRepository: MockGameRepository? = null
    private var roleRepository: MockGameRoleRepository? = null
    private var teamRepository: MockGameTeamRepository? = null
    private var userRepository: MockUserRepository? = null


    @Before
    fun initTests() {
        matchRepository = MockMatchRepository()
        playerRepository = MockMatchPlayerRepository()
        gameRepository = MockGameRepository()
        roleRepository = MockGameRoleRepository()
        teamRepository = MockGameTeamRepository()
        userRepository = MockUserRepository()

        matchHandler = MatchHandler(
                matchRepository,
                playerRepository,
                gameRepository,
                roleRepository,
                teamRepository,
                userRepository
        )
        userHandler = UserHandler(
                userRepository,
                matchRepository,
                roleRepository,
                teamRepository,
                gameRepository,
                playerRepository
        )

        gameHandler = GameHandler(
                gameRepository,
                roleRepository,
                teamRepository,
                matchRepository
        )
    }

    @Test
    fun saveAndGetMatch() {
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

        var userPlayer: User? = null
        try {
            userPlayer = userHandler!!.find(user.id).get()
        } catch (e: ExecutionException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        val player = MatchPlayer(userPlayer, gameRole, gameTeam, false)

        try {

            match.addMatchPlayer(player)
            val m = matchHandler!!.save(match).get()
            val id = m.id
            val result = matchHandler!!.find(id).get()
            assertEquals(match, result)
        } catch (e: ExecutionException) {
            e.printStackTrace()
            fail()
        } catch (e: InterruptedException) {
            e.printStackTrace()
            fail()
        }

    }

    @Test
    fun getAllTest() {
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

        val user = User("User name")
        try {
            userHandler!!.save(user).get()
        } catch (e: ExecutionException) {

        } catch (e: InterruptedException) {
        }

        val gameTeam = game.teams[0]
        val gameRole = game.teams[0].roles[0]

        var userPlayer: User? = null
        try {
            userPlayer = userHandler!!.find(user.id).get()
        } catch (e: ExecutionException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        val player = MatchPlayer(userPlayer, gameRole, gameTeam, false)
        val player2 = MatchPlayer(userPlayer, gameRole, gameTeam, false)
        val player3 = MatchPlayer(userPlayer, gameRole, gameTeam, false)

        val match1 = Match(game)
        match1.addMatchPlayer(player)
        val match2 = Match(game)
        match2.addMatchPlayer(player2)
        val match3 = Match(game)
        match3.addMatchPlayer(player3)

        try {
            matchHandler!!.save(match1).get()
            matchHandler!!.save(match2).get()
            matchHandler!!.save(match3).get()
            val size = matchHandler!!.all().get().size
            assertEquals(3, size.toLong())
        } catch (e: InterruptedException) {
            e.printStackTrace()
            fail()
        } catch (e: ExecutionException) {
            e.printStackTrace()
            fail()
        }

    }

    @Test(expected = IllegalArgumentException::class)
    fun addMatchWithGameWithoutId() {
        val game = Game(
                "Avalon",
                "Cool game where you laugh when someone accuses you of being Oberon",
                4,
                5,
                10
        )

        val match = Match(game)

        try {
            val m = matchHandler!!.save(match).get()
        } catch (ignored: ExecutionException) {
        } catch (ignored: InterruptedException) {
        }

    }

    @Test(expected = IllegalArgumentException::class)
    fun addMatchWithGameWithoutPlayers() {
        val game = Game(
                "Avalon",
                "Cool game where you laugh when someone accuses you of being Oberon",
                4,
                5,
                10
        )
        game.id = "0"

        val match = Match(game)

        try {
            val m = matchHandler!!.save(match).get()
        } catch (ignored: ExecutionException) {
        } catch (ignored: InterruptedException) {
        }

    }
}