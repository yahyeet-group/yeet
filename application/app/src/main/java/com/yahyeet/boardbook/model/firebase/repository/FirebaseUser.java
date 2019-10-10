package com.yahyeet.boardbook.model.firebase.repository;

import java.util.List;

public class FirebaseUser {
    private String id;
    private String name;
    private List<String> friends;

    public FirebaseUser() {
    }

    public FirebaseUser(String id, String name, List<String> friends) {
        this.id = id;
        this.name = name;
        this.friends = friends;
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

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }
}
