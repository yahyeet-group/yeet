package com.yahyeet.boardbook.model.entity;

public class User extends Entity {
    private String name;

    public User(String id, String name) {
        super(id);
        this.name = name;
    }

    public User() {
        super();
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
