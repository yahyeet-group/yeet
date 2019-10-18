package com.yahyeet.boardbook.model.entity;

public class MatchPlayer extends AbstractEntity {
    private User user;
    private GameRole role;
    private Match match;
    private boolean win;

    public MatchPlayer(User user, GameRole role, Match match, boolean win) {
        this.user = user;
        this.role = role;
        this.match = match;
        this.win = win;
    }

    public MatchPlayer(String id) {
        super(id);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public GameRole getRole() {
        return role;
    }

    public void setRole(GameRole role) {
        this.role = role;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public boolean getWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }
}
