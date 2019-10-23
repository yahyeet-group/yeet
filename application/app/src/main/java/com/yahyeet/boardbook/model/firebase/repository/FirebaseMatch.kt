package com.yahyeet.boardbook.model.firebase.repository

import com.google.firebase.firestore.DocumentSnapshot
import com.yahyeet.boardbook.model.entity.Game
import com.yahyeet.boardbook.model.entity.Match

import java.util.HashMap

class FirebaseMatch : AbstractFirebaseEntity<Match> {
    var gameId: String? = null

    constructor() {}

    constructor(id: String, gameId: String) : super(id) {
        this.gameId = gameId
    }

    override fun toMap(): Map<String, Any> {
        val map = HashMap<String, Any>()

        if (gameId != null) {
            map["gameId"] = gameId!!
        }

        return map
    }

    override fun toModelType(): Match {
        val match = Match(id)

        match.game = Game(gameId)

        return match
    }

    companion object {

        fun fromModelType(match: Match): FirebaseMatch {
            return FirebaseMatch(match.id, match.game!!.id)
        }

        fun fromDocument(document: DocumentSnapshot): FirebaseMatch {
            val firebaseMatch = FirebaseMatch()
            firebaseMatch.id = document.id

            if (document.contains("gameId")) {
                firebaseMatch.gameId = document.getString("gameId")
            }

            return firebaseMatch
        }
    }
}
