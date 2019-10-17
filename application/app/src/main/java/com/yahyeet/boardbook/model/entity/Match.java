package com.yahyeet.boardbook.model.entity;

import java.util.ArrayList;
import java.util.List;

public class Match extends AbstractEntity {
	private List<MatchPlayer> matchPlayers;

	public Match() {
		matchPlayers = new ArrayList<>();
	}

	public Match(String id) {
		super(id);
	}

	public List<MatchPlayer> getMatchPlayers() {
		return matchPlayers;
	}

	public void addMatchPlayer(MatchPlayer matchPlayer) {
		matchPlayer.setMatch(this);
		matchPlayers.add(matchPlayer);
	}
}
