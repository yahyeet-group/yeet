package com.yahyeet.boardbook.model.entity;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class User extends AbstractEntity {
    private String name;
    private List<User> friends;
    private List<Match> matches;

    public User(String name) {
        this.name = name;
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

    public void addFriend(User friend) {
        friends.add(friend);
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void addMatch(Match match) {
        matches.add(match);
    }
}
