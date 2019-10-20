package com.yahyeet.boardbook.model.entity;

public class GameRole extends AbstractEntity {
	private String name;
	private GameTeam team;

	public GameRole(String name) {
		this.name = name;
	}

	public GameRole() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public GameTeam getTeam() {
		return team;
	}

	public void setTeam(GameTeam team) {
		this.team = team;
	}
}
