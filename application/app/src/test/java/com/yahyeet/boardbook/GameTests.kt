package com.yahyeet.boardbook

import com.yahyeet.boardbook.model.entity.Game
import com.yahyeet.boardbook.model.entity.GameRole
import com.yahyeet.boardbook.model.entity.GameTeam

import org.junit.Test

import org.junit.Assert.*

class GameTests {

    private val testGame: Game
        get() = Game("Avalon", "Descriptive Description", 3, 2, 7)

    //TODO Write test for seeing if name,desc.... are correct, doesn't seem very important


    /// See if you can add a team
    @Test
    fun addTeam() {
        val game = testGame
        game.teams.add(GameTeam("SOM"))
        assertEquals("SOM", game.teams[0].name)
        game.teams.add(GameTeam("LSOA"))
        assertEquals("LSOA", game.teams[1].name)
    }

    /// See if you can add roles to a team
    @Test
    fun addRole() {
        val game = testGame

        val lsoaTeam = GameTeam("LSOA")
        lsoaTeam.roles.add(GameRole("Merlin"))
        game.teams.add(lsoaTeam)

        assertEquals("Merlin", game.teams[0].roles[0].name)

        val somTeam = GameTeam("SOM")
        somTeam.roles.add(GameRole("Mordred"))
        game.teams.add(somTeam)
        assertEquals("Mordred", game.teams[1].roles[0].name)
    }

}

