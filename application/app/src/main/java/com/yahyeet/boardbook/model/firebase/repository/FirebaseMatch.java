package com.yahyeet.boardbook.model.firebase.repository;

public class FirebaseMatch {
	private String id;
	private String gameId;

	public FirebaseMatch() {
	}

	public FirebaseMatch(String id, String gameId) {
		this.id = id;
		this.gameId = gameId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
}
