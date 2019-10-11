package com.yahyeet.boardbook.model.entity;

import java.util.ArrayList;
import java.util.List;

public class GameTeam extends AbstractEntity {
    private String name;
    private List<GameRole> roles;

    public GameTeam(String name) {
        this.name = name;
        roles = new ArrayList<>();
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

    public void setRoles(List<GameRole> roles) {
        this.roles = roles;
    }
}
