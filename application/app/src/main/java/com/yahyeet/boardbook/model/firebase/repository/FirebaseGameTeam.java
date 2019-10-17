package com.yahyeet.boardbook.model.firebase.repository;

import com.yahyeet.boardbook.model.entity.AbstractEntity;
import com.yahyeet.boardbook.model.entity.GameTeam;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class FirebaseGameTeam implements Serializable {

	private String id;
	private String name;
	private String gameId;

	public FirebaseGameTeam() {
	}

	public FirebaseGameTeam(String id, String name, String gameId) {
		this.id = id;
		this.name = name;
		this.gameId = gameId;
	}

	Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<>();

		if (name != null) {
			map.put("name", name);
		}

		if (gameId != null) {
			map.put("gameId", gameId);
		}

		return map;
	}


	static FirebaseGameTeam fromGameTeam(GameTeam gameTeam) {
		return new FirebaseGameTeam(gameTeam.getId(), gameTeam.getName(), gameTeam.getGame().getId());
	}

	GameTeam toGameTeam() {
		GameTeam gameTeam = new GameTeam(name);
		gameTeam.setId(id);
		return gameTeam;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
