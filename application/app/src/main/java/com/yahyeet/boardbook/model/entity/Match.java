package com.yahyeet.boardbook.model.entity;

import java.util.List;

public class Match extends AbstractEntity {
    private List<MatchPlayer> players;
    private Game game;

    public Match(String id, List<MatchPlayer> players, Game game) {
        super(id);
        this.players = players;
        this.game = game;
    }

    public Match() {
    }

    public List<MatchPlayer> getPlayers() {
        return players;
    }

    public void setPlayers(List<MatchPlayer> players) {
        this.players = players;
    }

    public MatchPlayer getMatchPlayerByUser(User user){
        for (MatchPlayer player: players) {
            if(player.getUser().equals(user)){
                return player;
            }
        }
        return null;
    }

    public Game getGame() {
        return game;
    }
}
