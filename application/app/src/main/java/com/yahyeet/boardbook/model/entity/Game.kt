package com.yahyeet.boardbook.model.entity

import java.util.ArrayList


class Game : AbstractEntity {
    var name: String? = null
    var description: String? = null
    var minPlayers: Int = 0
    var maxPlayers: Int = 0
    var difficulty: Int = 0
    private val teams = ArrayList<GameTeam>()
    private val matches = ArrayList<Match>()

    constructor(name: String,
                description: String,
                difficulty: Int,
                minPlayers: Int,
                maxPlayers: Int) {
        this.name = name
        this.description = description
        this.difficulty = difficulty
        this.minPlayers = minPlayers
        this.maxPlayers = maxPlayers
    }

    constructor(id: String) : super(id) {}


    //Nox has added this in purely for testing purposes
    constructor(name: String, description: String) {
        this.name = name
        this.description = description
    }

    fun getTeams(): List<GameTeam> {
        return teams
    }

    fun addTeam(team: GameTeam) {
        team.game = this
        teams.add(team)
    }

    fun getMatches(): List<Match> {
        return matches
    }

    fun addMatch(match: Match) {
        matches.add(match)
    }
}


