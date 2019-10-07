package com.yahyeet.boardbook.model.entity;

public class GameTeam extends AbstractEntity {
    private String name;


    public GameTeam(String id, String name) {
        super(id);
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
}
