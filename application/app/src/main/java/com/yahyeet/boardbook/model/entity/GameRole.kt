package com.yahyeet.boardbook.model.entity

class GameRole : AbstractEntity {
    var name: String? = null
    var team: GameTeam? = null

    constructor(name: String) {
        this.name = name
    }

    constructor() {}
}
