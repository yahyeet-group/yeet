package com.yahyeet.boardbook.model.entity;

public class User extends Entity {
    private String name;
    private String email;

    public User(String id, String name, String email) {
        super(id);
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
