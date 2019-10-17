package com.yahyeet.boardbook.model.firebase.repository;

import com.google.firebase.firestore.DocumentSnapshot;
import com.yahyeet.boardbook.model.entity.Game;

import java.util.HashMap;
import java.util.Map;

public class FirebaseGame extends FirebaseEntity<Game> {
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
		super(id);
		this.name = name;
		this.description = description;
		this.difficulty = difficulty;
		this.minPlayers = minPlayers;
		this.maxPlayers = maxPlayers;

	}

	@Override
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

	@Override
	public Game toModelType() {
		Game game = new Game(name, description, difficulty, minPlayers, maxPlayers);
		game.setId(getId());
		return game;
	}

	public static FirebaseGame fromModelType(Game game) {
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

	public static FirebaseGame fromDocument(DocumentSnapshot document) {
		FirebaseGame firebaseGame = new FirebaseGame();
		firebaseGame.setId(document.getId());

		if (document.contains("description")) {
			firebaseGame.setDescription(document.getString("description"));
		}

		if (document.contains("difficulty")) {
			firebaseGame.setDifficulty(document.getLong("difficulty").intValue());
		}

		if (document.contains("maxPlayers")) {
			firebaseGame.setMaxPlayers(document.getLong("maxPlayers").intValue());
		}

		if (document.contains("minPlayers")) {
			firebaseGame.setMinPlayers(document.getLong("minPlayers").intValue());
		}

		if (document.contains("name")) {
			firebaseGame.setName(document.getString("name"));
		}

		return firebaseGame;
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
