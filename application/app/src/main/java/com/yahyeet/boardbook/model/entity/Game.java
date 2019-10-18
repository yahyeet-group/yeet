package com.yahyeet.boardbook.model.entity;

import java.util.ArrayList;
import java.util.List;

public class Game extends AbstractEntity {
	private String name;
	private String description;
	private int minPlayers;
	private int maxPlayers;
	private int difficulty;
	private List<GameTeam> teams = new ArrayList<>();
	private List<Match> matches = new ArrayList<>();

	public Game(String name,
							String description,
							int difficulty,
							int minPlayers,
							int maxPlayers) {
		this.name = name;
		this.description = description;
		this.difficulty = difficulty;
		this.minPlayers = minPlayers;
		this.maxPlayers = maxPlayers;
	}

	public Game(String id) {
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public int getMinPlayers() {
		return minPlayers;
	}

	public void setMinPlayers(int minPlayers) {
		this.minPlayers = minPlayers;
	}

	public int getMaxPlayers() {
		return maxPlayers;
	}

	public void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}

	public List<GameTeam> getTeams() {
		return teams;
	}

	public void addTeam(GameTeam team) {
		team.setGame(this);
		teams.add(team);
	}

	public List<Match> getMatches() {
		return matches;
	}

	public void addMatch(Match match) {
		matches.add(match);
	}
}


