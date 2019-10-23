package com.yahyeet.boardbook.model.util

import com.yahyeet.boardbook.model.entity.Match
import com.yahyeet.boardbook.model.entity.MatchPlayer
import com.yahyeet.boardbook.model.entity.User

class StatisticsUtil {

    fun getWinrateFromMatches(matches: List<Match>, user: User): Double {
        val numberOfMatches = matches.size
        var numberOfWins = 0

        for (match in matches) {
            val mp = match.getMatchPlayerByUser(user)
            if (mp != null && mp.win) {
                numberOfWins++
            }
        }
        return if (numberOfMatches == 0) {
            0.0
        } else numberOfWins.toDouble() / numberOfMatches
    }
}
