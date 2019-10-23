package com.yahyeet.boardbook.model.firebase.repository

import com.google.firebase.firestore.DocumentSnapshot
import com.yahyeet.boardbook.model.entity.Game

import java.util.HashMap

class FirebaseGame : AbstractFirebaseEntity<Game> {
    var name: String? = null
    var description: String? = null
    var difficulty: Int = 0
    var minPlayers: Int = 0
    var maxPlayers: Int = 0

    constructor() {}

    constructor(id: String,
                name: String,
                description: String,
                difficulty: Int,
                minPlayers: Int,
                maxPlayers: Int) : super(id) {
        this.name = name
        this.description = description
        this.difficulty = difficulty
        this.minPlayers = minPlayers
        this.maxPlayers = maxPlayers

    }

    override fun toMap(): Map<String, Any> {
        val map = HashMap<String, Any>()
        if (name != null) {
            map["name"] = name!!
        }

        if (description != null) {
            map["description"] = description!!
        }

        map["difficulty"] = difficulty

        map["minPlayers"] = minPlayers

        map["maxPlayers"] = maxPlayers

        return map
    }

    override fun toModelType(): Game {
        val game = Game(name, description, difficulty, minPlayers, maxPlayers)
        game.id = id
        return game
    }

    companion object {

        fun fromModelType(game: Game): FirebaseGame {

            return FirebaseGame(
                    game.id,
                    game.name,
                    game.description,
                    game.difficulty,
                    game.minPlayers,
                    game.maxPlayers
            )
        }

        fun fromDocument(document: DocumentSnapshot): FirebaseGame {
            val firebaseGame = FirebaseGame()
            firebaseGame.id = document.id

            if (document.contains("description")) {
                firebaseGame.description = document.getString("description")
            }

            if (document.contains("difficulty")) {
                firebaseGame.difficulty = document.getLong("difficulty")!!.toInt()
            }

            if (document.contains("maxPlayers")) {
                firebaseGame.maxPlayers = document.getLong("maxPlayers")!!.toInt()
            }

            if (document.contains("minPlayers")) {
                firebaseGame.minPlayers = document.getLong("minPlayers")!!.toInt()
            }

            if (document.contains("name")) {
                firebaseGame.name = document.getString("name")
            }

            return firebaseGame
        }
    }
}
