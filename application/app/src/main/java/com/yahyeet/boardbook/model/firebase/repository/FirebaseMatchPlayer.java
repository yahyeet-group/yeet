package com.yahyeet.boardbook.model.firebase.repository;

public class FirebaseMatchPlayer {
	private String id;
	private boolean win;
	private String playerId;
	private String roleId;
	private String matchId;

	public FirebaseMatchPlayer() {
	}

	public FirebaseMatchPlayer(String id, boolean win, String playerId, String roleId, String matchId) {
		this.id = id;
		this.win = win;
		this.playerId = playerId;
		this.roleId = roleId;
		this.matchId = matchId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isWin() {
		return win;
	}

	public void setWin(boolean win) {
		this.win = win;
	}

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getMatchId() {
		return matchId;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}
}
