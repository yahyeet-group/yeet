package com.yahyeet.boardbook.model.firebase.repository;

import com.yahyeet.boardbook.model.entity.User;

import java.util.HashMap;
import java.util.Map;

class FirebaseUser {
    private String id;
    private String name;

    public FirebaseUser() {}

    public FirebaseUser(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> data = new HashMap<>();

        if (name != null) {
            data.put("name", name);
        }

        return data;
    }

    public static FirebaseUser fromUser(User user) {
        return new FirebaseUser(user.getId(), user.getName());
    }

    public User toUser() {
        User user = new User(name);
        user.setId(getId());
        return user;
    }
}
