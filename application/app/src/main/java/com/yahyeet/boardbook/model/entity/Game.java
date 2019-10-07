package com.yahyeet.boardbook.model.entity;

import java.util.List;

public class Game extends AbstractEntity {
    private String name;
    private String description;
    private int minPlayers;
    private int maxPlayers;
    private List<GameTeam> teams;
    private List<GameRole> roles;

    public Game(String id, String name, String description, int minPlayers, int maxPlayers, List<GameTeam> teams, List<GameRole> roles) {
        super(id);
        this.name = name;
        this.description = description;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.teams = teams;
        this.roles = roles;
    }

    public Game() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public List<GameTeam> getTeams() {
        return teams;
    }

    public void setTeams(List<GameTeam> teams) {
        this.teams = teams;
    }

    public List<GameRole> getRoles() {
        return roles;
    }

    public void setRoles(List<GameRole> roles) {
        this.roles = roles;
    }
}


