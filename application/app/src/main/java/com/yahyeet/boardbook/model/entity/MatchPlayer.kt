package com.yahyeet.boardbook.model.entity

class MatchPlayer : AbstractEntity {
    var user: User? = null
    var role: GameRole? = null
    var team: GameTeam? = null
    var match: Match? = null
    var win: Boolean = false

    constructor(user: User, role: GameRole, team: GameTeam, win: Boolean) {
        this.user = user
        this.role = role
        this.team = team
        this.win = win
    }

    constructor(id: String) : super(id) {}
}
