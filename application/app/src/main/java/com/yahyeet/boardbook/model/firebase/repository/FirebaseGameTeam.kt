package com.yahyeet.boardbook.model.firebase.repository

import com.google.firebase.firestore.DocumentSnapshot
import com.yahyeet.boardbook.model.entity.Game
import com.yahyeet.boardbook.model.entity.GameTeam

import java.util.HashMap

internal class FirebaseGameTeam : AbstractFirebaseEntity<GameTeam> {
    var name: String? = null
    var gameId: String? = null

    constructor() {}

    constructor(id: String, name: String, gameId: String) : super(id) {
        this.name = name
        this.gameId = gameId
    }

    override fun toMap(): Map<String, Any> {
        val map = HashMap<String, Any>()

        if (name != null) {
            map["name"] = name!!
        }

        if (gameId != null) {
            map["gameId"] = gameId!!
        }

        return map
    }

    override fun toModelType(): GameTeam {
        val gameTeam = GameTeam(name)
        gameTeam.id = id
        gameTeam.game = Game(gameId)
        return gameTeam
    }

    companion object {

        fun fromModelType(gameTeam: GameTeam): FirebaseGameTeam {
            return FirebaseGameTeam(gameTeam.id, gameTeam.name, gameTeam.game!!.id)
        }

        fun fromDocument(document: DocumentSnapshot): FirebaseGameTeam {
            val firebaseGameTeam = FirebaseGameTeam()
            firebaseGameTeam.id = document.id

            if (document.contains("name")) {
                firebaseGameTeam.name = document.getString("name")
            }

            if (document.contains("gameId")) {
                firebaseGameTeam.gameId = document.getString("gameId")
            }

            return firebaseGameTeam
        }
    }
}
