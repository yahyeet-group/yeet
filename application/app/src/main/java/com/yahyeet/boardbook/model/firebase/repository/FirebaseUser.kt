package com.yahyeet.boardbook.model.firebase.repository

import com.google.firebase.firestore.DocumentSnapshot
import com.yahyeet.boardbook.model.entity.User

import java.util.HashMap

internal class FirebaseUser : AbstractFirebaseEntity<User> {
    var name: String? = null

    constructor() {}

    constructor(id: String, name: String) : super(id) {
        this.name = name
    }

    override fun toMap(): Map<String, Any> {
        val data = HashMap<String, Any>()

        if (name != null) {
            data["name"] = name!!
        }

        return data
    }

    override fun toModelType(): User {
        val user = User(name)
        user.id = id
        return user
    }

    companion object {

        fun fromModelType(user: User): FirebaseUser {
            return FirebaseUser(user.id, user.name)
        }

        fun fromDocument(document: DocumentSnapshot): FirebaseUser {
            val firebaseUser = FirebaseUser()
            firebaseUser.id = document.id

            if (document.contains("name")) {
                firebaseUser.name = document.getString("name")
            }

            return firebaseUser
        }
    }
}
