package com.yahyeet.boardbook.model.entity;

public class MatchPlayer extends AbstractEntity {
    private User user;
    private GameRole role;
    private GameTeam team;
    private boolean win;

    public MatchPlayer(String id, User user, GameRole role, GameTeam team, boolean win) {
        super(id);
        this.user = user;
        this.role = role;
        this.team = team;
        this.win = win;
    }

    public MatchPlayer() {
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

    public GameTeam getTeam() {
        return team;
    }

    public void setTeam(GameTeam team) {
        this.team = team;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }
}
