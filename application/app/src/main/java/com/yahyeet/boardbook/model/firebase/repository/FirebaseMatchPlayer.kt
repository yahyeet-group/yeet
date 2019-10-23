package com.yahyeet.boardbook.model.firebase.repository

import com.google.firebase.firestore.DocumentSnapshot
import com.yahyeet.boardbook.model.entity.GameRole
import com.yahyeet.boardbook.model.entity.GameTeam
import com.yahyeet.boardbook.model.entity.Match
import com.yahyeet.boardbook.model.entity.MatchPlayer
import com.yahyeet.boardbook.model.entity.User

import java.util.HashMap

class FirebaseMatchPlayer : AbstractFirebaseEntity<MatchPlayer> {
    var isWin: Boolean = false
    var playerId: String? = null
    var roleId: String? = null
    var teamId: String? = null
    var matchId: String? = null

    constructor() {}

    constructor(id: String, win: Boolean, playerId: String, roleId: String, teamId: String, matchId: String) : super(id) {
        this.isWin = win
        this.playerId = playerId
        this.roleId = roleId
        this.teamId = teamId
        this.matchId = matchId
    }

    override fun toMap(): Map<String, Any> {
        val map = HashMap<String, Any>()

        if (matchId != null) {
            map["matchId"] = matchId!!
        } else {
            throw IllegalArgumentException("Match id must be set")
        }

        if (playerId != null) {
            map["playerId"] = playerId!!
        } else {
            throw IllegalArgumentException("Player id must be set")
        }

        map["roleId"] = roleId!!

        map["teamId"] = teamId!!

        map["win"] = isWin

        return map
    }

    override fun toModelType(): MatchPlayer {
        val matchPlayer = MatchPlayer(id)
        matchPlayer.match = Match(matchId)

        val user = User()
        user.id = playerId
        matchPlayer.user = user

        if (teamId != null) {
            val gameTeam = GameTeam()
            gameTeam.id = teamId
            matchPlayer.team = gameTeam
        }

        if (roleId != null) {
            val gameRole = GameRole()
            gameRole.id = roleId
            matchPlayer.role = gameRole
        }

        return matchPlayer
    }

    companion object {

        fun fromModelType(matchPlayer: MatchPlayer): FirebaseMatchPlayer {
            return FirebaseMatchPlayer(
                    matchPlayer.id,
                    matchPlayer.win,
                    matchPlayer.user!!.id,
                    if (matchPlayer.role != null) matchPlayer.role!!.id else null,
                    if (matchPlayer.team != null) matchPlayer.team!!.id else null,
                    matchPlayer.match!!.id
            )
        }

        fun fromDocument(document: DocumentSnapshot): FirebaseMatchPlayer {
            val firebaseMatchPlayer = FirebaseMatchPlayer()
            firebaseMatchPlayer.id = document.id

            if (document.contains("matchId")) {
                firebaseMatchPlayer.matchId = document.getString("matchId")
            }

            if (document.contains("playerId")) {
                firebaseMatchPlayer.playerId = document.getString("playerId")
            }

            if (document.contains("roleId")) {
                firebaseMatchPlayer.roleId = document.getString("roleId")
            }

            if (document.contains("teamId")) {
                firebaseMatchPlayer.teamId = document.getString("teamId")
            }

            if (document.contains("win")) {
                firebaseMatchPlayer.isWin = document.getBoolean("win")
            }

            return firebaseMatchPlayer
        }
    }
}
