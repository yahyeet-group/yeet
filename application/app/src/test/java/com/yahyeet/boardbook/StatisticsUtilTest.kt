package com.yahyeet.boardbook

import com.yahyeet.boardbook.model.entity.Game
import com.yahyeet.boardbook.model.entity.GameRole
import com.yahyeet.boardbook.model.entity.GameTeam
import com.yahyeet.boardbook.model.entity.Match
import com.yahyeet.boardbook.model.entity.MatchPlayer
import com.yahyeet.boardbook.model.entity.User
import com.yahyeet.boardbook.model.util.StatisticsUtil

import org.junit.Assert
import org.junit.Test

import java.util.ArrayList
import java.util.Stack

import junit.framework.TestCase.assertEquals

class StatisticsUtilTest {

    @Test
    fun getWinrateFromMatches() {
        val user = User()
        user.id = "hej"
        val stat = StatisticsUtil()
        val game = Game("Avalon", "Cool game", 1, 2, 3)
        val lostMatch = Match(game)
        lostMatch.addMatchPlayer(MatchPlayer(user, GameRole("he"), null, false))
        user.addMatch(lostMatch)

        val wonMatch = Match(game)
        wonMatch.addMatchPlayer(MatchPlayer(user, GameRole("he"), null, true))
        user.addMatch(wonMatch)

        val d = stat.getWinrateFromMatches(user.matches, user)

        assertEquals(0.5, d)
    }

    @Test
    fun getWinrateFromMatchesIfNoMatches() {
        val user = User()
        user.id = "hej"
        val stat = StatisticsUtil()

        user.addMatch(Match("i"))

        val d = stat.getWinrateFromMatches(user.matches, user)

        assertEquals(0.0, d)
    }
}
