package com.yahyeet.boardbook.model.firebase.repository;

import com.google.firebase.firestore.DocumentSnapshot;
import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.model.entity.Match;

import java.util.HashMap;
import java.util.Map;

public class FirebaseMatch extends AbstractFirebaseEntity<Match> {
	private String gameId;

	public FirebaseMatch() {
	}

	public FirebaseMatch(String id, String gameId) {
		super(id);
		this.gameId = gameId;
	}

	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<>();

		if (gameId != null) {
			map.put("gameId", gameId);
		}

		return map;
	}

	@Override
	public Match toModelType() {
		Match match = new Match(getId());

		match.setGame(new Game(gameId));

		return match;
	}

	public static FirebaseMatch fromModelType(Match match) {
		return new FirebaseMatch(match.getId(), match.getGame().getId());
	}

	public static FirebaseMatch fromDocument(DocumentSnapshot document) {
		FirebaseMatch firebaseMatch = new FirebaseMatch();
		firebaseMatch.setId(document.getId());

		if (document.contains("gameId")) {
			firebaseMatch.setGameId(document.getString("gameId"));
		}

		return firebaseMatch;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
}
