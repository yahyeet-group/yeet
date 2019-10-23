package com.yahyeet.boardbook.model.entity

import java.util.ArrayList

class User : AbstractEntity {
    var name: String? = null
    val friends = ArrayList<User>()
    val matches = ArrayList<Match>()

    constructor(name: String) {
        this.name = name
    }

    constructor() {}

    fun getFriends(): List<User> {
        return friends
    }

    fun addFriend(friend: User) {
        if (getFriends().stream().anyMatch { f -> f == friend }) {
            return
        }
        friends.add(friend)
        friend.addFriend(this)
    }

    fun getMatches(): List<Match> {
        return matches
    }

    fun addMatch(match: Match) {
        matches.add(match)
    }
}
