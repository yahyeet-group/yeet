package com.yahyeet.boardbook.presenter.matchcreation


import com.yahyeet.boardbook.R
import com.yahyeet.boardbook.activity.matchcreation.ICreateMatchActivity
import com.yahyeet.boardbook.activity.matchcreation.configureteams.ConfigureTeamsFragment
import com.yahyeet.boardbook.activity.matchcreation.CreateMatchActivity
import com.yahyeet.boardbook.activity.matchcreation.selectgame.SelectGameFragment
import com.yahyeet.boardbook.activity.matchcreation.selectplayers.SelectPlayersFragment
import com.yahyeet.boardbook.model.entity.Match
import com.yahyeet.boardbook.model.entity.MatchPlayer
import com.yahyeet.boardbook.presenter.BoardbookSingleton

class CMMasterPresenter(private val activity: ICreateMatchActivity) {

    val cmdh: MatchCreationDataObject

    init {
        cmdh = MatchCreationDataObject()
    }

    fun goToConfigureTeams() {
        activity.goToConfigureTeams()
    }

    fun goToSelectGame() {
        activity.goToSelectGame()
    }

    fun goToSelectPlayers() {
        activity.goToSelectPlayers()
    }

    fun finalizeMatch() {
        val finalMatch = Match(cmdh.game)
        for (mp in cmdh.players) {
            finalMatch.addMatchPlayer(mp)
        }
        println()
        BoardbookSingleton.getInstance().matchHandler
                .save(finalMatch)
                .thenAccept { u -> activity.finalizeMatchCreation() }
                .exceptionally { e ->

                    // TODO: Handle firebase not able to save match, dunno what to do exactly
                    e.printStackTrace()
                    null
                }
    }
}
