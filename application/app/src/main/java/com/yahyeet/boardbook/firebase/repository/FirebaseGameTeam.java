package com.yahyeet.boardbook.firebase.repository;

import com.google.firebase.firestore.DocumentSnapshot;
import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.model.entity.GameTeam;

import java.util.HashMap;
import java.util.Map;

class FirebaseGameTeam extends AbstractFirebaseEntity<GameTeam> {
	private String name;
	private String gameId;

	public FirebaseGameTeam() {
	}

	public FirebaseGameTeam(String id, String name, String gameId) {
		super(id);
		this.name = name;
		this.gameId = gameId;
	}

	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<>();

		if (name != null) {
			map.put("name", name);
		}

		if (gameId != null) {
			map.put("gameId", gameId);
		}

		return map;
	}

	@Override
	public GameTeam toModelType() {
		GameTeam gameTeam = new GameTeam(name);
		gameTeam.setId(getId());
		gameTeam.setGame(new Game(gameId));
		return gameTeam;
	}

	/**
	 * Creates a Firebase game team entity from a model game team
	 *
	 * @param gameTeam Model game team
	 * @return Firebase game team
	 */
	public static FirebaseGameTeam fromModelType(GameTeam gameTeam) {
		return new FirebaseGameTeam(gameTeam.getId(), gameTeam.getName(), gameTeam.getGame().getId());
	}

	/**
	 * Create a Firebase game team from a Firebase document snapshot
	 *
	 * @param document Firebase document snapshot
	 * @return Firebase game team
	 */
	public static FirebaseGameTeam fromDocument(DocumentSnapshot document) {
		FirebaseGameTeam firebaseGameTeam = new FirebaseGameTeam();
		firebaseGameTeam.setId(document.getId());

		if (document.contains("name")) {
			firebaseGameTeam.setName(document.getString("name"));
		}

		if (document.contains("gameId")) {
			firebaseGameTeam.setGameId(document.getString("gameId"));
		}

		return firebaseGameTeam;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
}
