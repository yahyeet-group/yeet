package com.yahyeet.boardbook.model.entity;

public class GameRole extends AbstractEntity {
    private String name;


    public GameRole(String id, String name) {
        super(id);
        this.name = name;
    }

    public GameRole() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
