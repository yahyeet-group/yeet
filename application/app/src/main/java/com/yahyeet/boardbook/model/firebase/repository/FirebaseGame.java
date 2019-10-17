package com.yahyeet.boardbook.model.firebase.repository;

import com.yahyeet.boardbook.model.entity.AbstractEntity;
import com.yahyeet.boardbook.model.entity.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FirebaseGame {
	private String id;
	private String name;
	private String description;
	private int difficulty;
	private int minPlayers;
	private int maxPlayers;

	public FirebaseGame() {
	}

	public FirebaseGame(String id,
											String name,
											String description,
											int difficulty,
											int minPlayers,
											int maxPlayers) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.difficulty = difficulty;
		this.minPlayers = minPlayers;
		this.maxPlayers = maxPlayers;

	}

	public static FirebaseGame fromGame(Game game) {
		FirebaseGame firebaseGame = new FirebaseGame(
			game.getId(),
			game.getName(),
			game.getDescription(),
			game.getDifficulty(),
			game.getMinPlayers(),
			game.getMaxPlayers()
		);

		return firebaseGame;
	}

	public Game toGame() {
		Game game = new Game(name, description, difficulty, minPlayers, maxPlayers);
		game.setId(getId());
		return game;
	}

	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<>();
		if (name != null) {
			map.put("name", name);
		}

		if (description != null) {
			map.put("description", description);
		}

		map.put("difficulty", difficulty);

		map.put("minPlayers", minPlayers);

		map.put("maxPlayers", maxPlayers);

		return map;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
}
