package com.yahyeet.boardbook.model.entity

import java.util.ArrayList
import java.util.Optional

class Match : AbstractEntity {
    private var matchPlayers = ArrayList<MatchPlayer>()
    var game: Game? = null

    constructor(game: Game) {
        matchPlayers = ArrayList()
        this.game = game
    }

    constructor(id: String) : super(id) {}

    fun getMatchPlayers(): List<MatchPlayer> {
        return matchPlayers
    }

    fun addMatchPlayer(matchPlayer: MatchPlayer) {
        matchPlayer.match = this
        matchPlayers.add(matchPlayer)
    }

    fun getMatchPlayerByUser(user: User): MatchPlayer {
        val optionalMatchPlayer = matchPlayers
                .stream()
                .filter { matchPlayer -> matchPlayer.user!!.id == user.id }
                .findFirst()

        return optionalMatchPlayer.orElse(null)
    }
}
