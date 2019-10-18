package com.yahyeet.boardbook.model.firebase.repository;

import com.google.firebase.firestore.DocumentSnapshot;
import com.yahyeet.boardbook.model.entity.MatchPlayer;

import java.util.HashMap;
import java.util.Map;

public class FirebaseMatchPlayer extends AbstractFirebaseEntity<MatchPlayer> {
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

	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<>();

		if (matchId != null) {
			map.put("matchId", matchId);
		}

		if (playerId != null) {
			map.put("playerId", playerId);
		}

		if (roleId != null) {
			map.put("roleId", roleId);
		}

		map.put("win", win);

		return map;
	}

	@Override
	public MatchPlayer toModelType() {
		return new MatchPlayer(id);
	}

	public static FirebaseMatchPlayer fromModelType(MatchPlayer matchPlayer) {
		return new FirebaseMatchPlayer(
			matchPlayer.getId(),
			matchPlayer.getWin(),
			matchPlayer.getUser().getId(),
			matchPlayer.getRole().getId(),
			matchPlayer.getMatch().getId()
		);
	}

	public static FirebaseMatchPlayer fromDocument(DocumentSnapshot document) {
		FirebaseMatchPlayer firebaseMatchPlayer = new FirebaseMatchPlayer();
		firebaseMatchPlayer.setId(document.getId());

		if (document.contains("matchId")) {
			firebaseMatchPlayer.setMatchId(document.getString("matchId"));
		}

		if (document.contains("playerId")) {
			firebaseMatchPlayer.setPlayerId(document.getString("playerId"));
		}

		if (document.contains("roleId")) {
			firebaseMatchPlayer.setRoleId(document.getString("roleId"));
		}

		if (document.contains("win")) {
			firebaseMatchPlayer.setWin(document.getBoolean("win"));
		}

		return firebaseMatchPlayer;
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
