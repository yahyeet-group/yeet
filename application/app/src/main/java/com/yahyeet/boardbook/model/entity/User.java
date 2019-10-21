package com.yahyeet.boardbook.model.entity;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class User extends AbstractEntity {
    private String name;
    private List<User> friends = new ArrayList<>();
    private List<Match> matches = new ArrayList<>();

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
        if(getFriends().stream().anyMatch(f -> f.equals(friend))){
            return;
        }
        friends.add(friend);
        friend.addFriend(this);
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void addMatch(Match match) {
        matches.add(match);
    }
}
