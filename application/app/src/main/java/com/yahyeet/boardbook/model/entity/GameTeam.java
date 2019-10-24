package com.yahyeet.boardbook.model.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Defines a game team entity
 */
public class GameTeam extends AbstractEntity {
    private String name;
    private List<GameRole> roles = new ArrayList<>();
    private Game game;

    public GameTeam(String name) {
        this.name = name;
    }

    public GameTeam() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GameRole> getRoles() {
        return roles;
    }

    public void addRole(GameRole role) {
        role.setTeam(this);
        roles.add(role);
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
