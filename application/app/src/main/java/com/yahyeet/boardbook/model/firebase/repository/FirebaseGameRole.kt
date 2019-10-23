package com.yahyeet.boardbook.model.firebase.repository

import com.google.firebase.firestore.DocumentSnapshot
import com.yahyeet.boardbook.model.entity.GameRole
import com.yahyeet.boardbook.model.entity.GameTeam

import java.util.HashMap

class FirebaseGameRole : AbstractFirebaseEntity<GameRole> {
    var name: String? = null
    var teamId: String? = null

    constructor() {}

    constructor(id: String, name: String, teamId: String) : super(id) {
        this.name = name
        this.teamId = teamId
    }

    override fun toMap(): Map<String, Any> {
        val map = HashMap<String, Any>()

        if (name != null) {
            map["name"] = name!!
        }

        if (teamId != null) {
            map["teamId"] = teamId!!
        }

        return map
    }

    override fun toModelType(): GameRole {
        val gameRole = GameRole(name)
        gameRole.id = id
        val gameTeam = GameTeam()
        gameTeam.id = teamId
        gameRole.team = gameTeam
        return gameRole
    }

    companion object {

        fun fromModelType(gameRole: GameRole): FirebaseGameRole {
            return FirebaseGameRole(gameRole.id, gameRole.name, gameRole.team!!.id)
        }

        fun fromDocument(document: DocumentSnapshot): FirebaseGameRole {
            val firebaseGameRole = FirebaseGameRole()
            firebaseGameRole.id = document.id

            if (document.contains("name")) {
                firebaseGameRole.name = document.getString("name")
            }

            if (document.contains("teamId")) {
                firebaseGameRole.teamId = document.getString("teamId")
            }

            return firebaseGameRole
        }
    }
}
