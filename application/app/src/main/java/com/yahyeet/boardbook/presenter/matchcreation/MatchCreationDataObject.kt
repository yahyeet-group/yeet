package com.yahyeet.boardbook.presenter.matchcreation

import android.app.Activity

import com.yahyeet.boardbook.model.entity.Game
import com.yahyeet.boardbook.model.entity.GameRole
import com.yahyeet.boardbook.model.entity.GameTeam
import com.yahyeet.boardbook.model.entity.Match
import com.yahyeet.boardbook.model.entity.MatchPlayer
import com.yahyeet.boardbook.model.entity.User

import java.util.ArrayList
import java.util.stream.Collectors

class MatchCreationDataObject {
    //TODO See if this name should be kept

    private val players = ArrayList<MatchPlayer>()
    private val selectedPlayers = ArrayList<User>()

    var game: Game? = null
    // private Activity parentActivity;

    constructor(activity: Activity) {
        // TODO: Removed for now, but can get back if necessary later
        //this.parentActivity = activity;
    }

    constructor() {}

    fun getSelectedPlayers(): List<User> {
        return selectedPlayers
    }

    fun addSelectedPlayer(user: User) {
        selectedPlayers.add(user)
    }

    fun removeSelectedPlayer(user: User) {
        selectedPlayers.remove(user)
    }

    fun addPlayer(user: User, gameRole: GameRole, gameTeam: GameTeam, win: Boolean?) {
        val mp = MatchPlayer(user, gameRole, gameTeam, win!!)
        players.add(mp)
    }

    fun addPlayer(user: User, gameTeam: GameTeam, win: Boolean?) {
        val mp = MatchPlayer(user, null, gameTeam, win!!)
        players.add(mp)
    }

    fun addPlayer(user: User, win: Boolean?) {
        val mp = MatchPlayer(user, null, null, win!!)
        players.add(mp)
    }

    fun addPlayer(mp: MatchPlayer) {
        players.add(mp)
    }

    fun addPlayer(mpl: List<MatchPlayer>) {
        players.addAll(mpl)
    }

    fun getPlayers(): List<MatchPlayer> {
        return players
    }

    fun finalizeMatch(): Match {
        //TODO I suppose match should hold what game it is playing. Added game to match constructor // Vex
        val match = Match(game)
        players.forEach(Consumer<MatchPlayer> { match.addMatchPlayer(it) })
        return match
    }


}

