package com.yahyeet.boardbook.model.entity;

import java.time.LocalDateTime;
import java.util.List;

public class User extends AbstractEntity {
    private String name;
    private List<User> friends;
    private List<Match> matches;

    public User(String id, String name, List<User> friends, List<Match> matches) {
        super(id);
        this.name = name;
        this.friends = friends;
        this.matches = matches;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }
}
