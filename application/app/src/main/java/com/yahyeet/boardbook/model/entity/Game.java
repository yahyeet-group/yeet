package com.yahyeet.boardbook.model.entity;

import java.util.List;
import java.util.Map;

public class Game extends AbstractEntity {
    private String name;
    private String description;
    private String rules;
    private int minPlayers;
    private int maxPlayers;
    private int difficulty;
    private List<GameTeam> teams;

    public Game(String id,
                String name,
                String description,
                String rules,
                int difficulty,
                int minPlayers,
                int maxPlayers,
                List<GameTeam> teams) {
        setId(id);
        this.name = name;
        this.description = description;
        this.difficulty = difficulty;
        this.rules = rules;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.teams = teams;
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

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
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
}


