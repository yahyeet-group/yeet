package com.yahyeet.boardbook.model.entity;

import java.util.List;

public class Match extends AbstractEntity {
    private List<MatchPlayer> players;

    public Match(String id, List<MatchPlayer> players) {
        super(id);
        this.players = players;
    }

    public Match() {
    }

    public List<MatchPlayer> getPlayers() {
        return players;
    }

    public void setPlayers(List<MatchPlayer> players) {
        this.players = players;
    }
}
