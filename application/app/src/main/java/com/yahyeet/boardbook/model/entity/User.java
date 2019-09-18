package com.yahyeet.boardbook.model.entity;

public class User extends Entity {
    private String name;

    public User(String id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
