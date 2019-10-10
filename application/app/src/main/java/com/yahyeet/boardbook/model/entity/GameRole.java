package com.yahyeet.boardbook.model.entity;

public class GameRole extends AbstractEntity {
    private String name;


    public GameRole(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
