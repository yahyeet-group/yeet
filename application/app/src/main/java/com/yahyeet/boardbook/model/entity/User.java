package com.yahyeet.boardbook.model.entity;

public class User extends Entity {
    private String name;
    private String email;

    public User(String id, String name) {
        super(id);
        this.name = name;
    }

    public User() {
        super();
    }

    public User getThis() {
        return this;
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
