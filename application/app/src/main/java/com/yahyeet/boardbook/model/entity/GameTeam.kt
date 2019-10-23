package com.yahyeet.boardbook.model.entity

import java.util.ArrayList

class GameTeam : AbstractEntity {
    var name: String? = null
    private val roles = ArrayList<GameRole>()
    var game: Game? = null

    constructor(name: String) {
        this.name = name
    }

    constructor() {}

    fun getRoles(): List<GameRole> {
        return roles
    }

    fun addRole(role: GameRole) {
        role.team = this
        roles.add(role)
    }
}
