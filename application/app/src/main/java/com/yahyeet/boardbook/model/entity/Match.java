package com.yahyeet.boardbook.model.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Defines a match entity
 */
public class Match extends AbstractEntity {
	private List<MatchPlayer> matchPlayers = new ArrayList<>();
	private Game game;

	public Match(Game game) {
		matchPlayers = new ArrayList<>();
		this.game = game;
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

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public MatchPlayer getMatchPlayerByUser(User user) {
		Optional<MatchPlayer> optionalMatchPlayer = matchPlayers
			.stream()
			.filter(matchPlayer -> matchPlayer.getUser().getId().equals(user.getId()))
			.findFirst();

		return optionalMatchPlayer.orElse(null);
	}
}
