package com.yahyeet.boardbook.model

import com.yahyeet.boardbook.model.entity.Game
import com.yahyeet.boardbook.model.entity.GameRole
import com.yahyeet.boardbook.model.entity.GameTeam
import com.yahyeet.boardbook.model.handler.GameHandler
import com.yahyeet.boardbook.model.mock.repository.MockGameRepository
import com.yahyeet.boardbook.model.mock.repository.MockGameRoleRepository
import com.yahyeet.boardbook.model.mock.repository.MockGameTeamRepository
import com.yahyeet.boardbook.model.mock.repository.MockMatchRepository

import org.junit.Before
import org.junit.Test

import java.util.concurrent.ExecutionException

import org.junit.Assert.*

class GameHandlerTest {

    private var gameHandler: GameHandler? = null

    @Before
    fun initTests() {
        gameHandler = GameHandler(
                MockGameRepository(),
                MockGameRoleRepository(),
                MockGameTeamRepository(),
                MockMatchRepository()
        )
    }

    @Test
    fun gaveAndGetGame() {
        val game = Game(
                "Avalon",
                "Cool game where you laugh when someone accuses you of being Oberon",
                4,
                5,
                10
        )

        try {
            val id = gameHandler!!.save(game).get().id
            val result = gameHandler!!.find(id).get()
            assertEquals(game, result)
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
        val game1 = Game(
                "Avalon",
                "Cool game where you laugh when someone accuses you of being Oberon",
                4,
                5,
                10
        )

        val game2 = Game(
                "Coup",
                "I am a Duke",
                2,
                3,
                6
        )

        val game3 = Game(
                "Skull",
                "Do i have a flower or a skull. That is the question",
                1,
                2,
                8
        )

        try {
            gameHandler!!.save(game1).get()
            gameHandler!!.save(game2).get()
            gameHandler!!.save(game3).get()
            val size = gameHandler!!.all().get().size
            assertEquals(3, size.toLong())
        } catch (e: InterruptedException) {
            e.printStackTrace()
            fail()
        } catch (e: ExecutionException) {
            e.printStackTrace()
            fail()
        }

    }

    @Test
    fun saveGameWithRolesAndTeam() {
        val game = Game(
                "Avalon",
                "Cool game where you laugh when someone accuses you of being Oberon",
                4,
                5,
                10
        )
        val team = GameTeam()
        team.name = "Good"
        val role = GameRole()
        role.name = "Merlin"
        team.addRole(role)
        game.addTeam(team)

        try {
            val id = gameHandler!!.save(game).get().id
            val result = gameHandler!!.find(id).get()
            assertEquals(game, result)
        } catch (e: InterruptedException) {
            e.printStackTrace()
            fail()
        } catch (e: ExecutionException) {
            e.printStackTrace()
            fail()
        }

    }


}