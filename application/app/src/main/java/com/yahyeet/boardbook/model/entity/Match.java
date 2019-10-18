package com.yahyeet.boardbook.model.entity;

import java.util.ArrayList;
import java.util.List;

public class Match extends AbstractEntity {
	private List<MatchPlayer> matchPlayers;
	private Game game;

	public Match() {
		matchPlayers = new ArrayList<>();
	}

	public Match(String id) {
		super(id);
		matchPlayers = new ArrayList<>();
	}

	public List<MatchPlayer> getMatchPlayers() {
		return matchPlayers;
	}

	public void addMatchPlayer(MatchPlayer matchPlayer) {
		matchPlayer.setMatch(this);
		matchPlayers.add(matchPlayer);
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

    public MatchPlayer getMatchPlayerByUser(User user){
     return matchPlayer.stream().filter(matchPlayer -> matchPlayer.getUser().getId().equals(user.getId())).findFirst().get();
    }
}
