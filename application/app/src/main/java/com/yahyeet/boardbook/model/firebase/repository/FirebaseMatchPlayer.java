package com.yahyeet.boardbook.model.firebase.repository;

import com.google.firebase.firestore.DocumentSnapshot;
import com.yahyeet.boardbook.model.entity.MatchPlayer;

import java.util.HashMap;
import java.util.Map;

public class FirebaseMatchPlayer extends AbstractFirebaseEntity<MatchPlayer> {
	private boolean win;
	private String playerId;
	private String roleId;
	private String teamId;
	private String matchId;

	public FirebaseMatchPlayer() {
	}

	public FirebaseMatchPlayer(String id, boolean win, String playerId, String roleId, String teamId, String matchId) {
		super(id);
		this.win = win;
		this.playerId = playerId;
		this.roleId = roleId;
		this.teamId = teamId;
		this.matchId = matchId;
	}

	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<>();

		if (matchId != null) {
			map.put("matchId", matchId);
		} else {
			throw new IllegalArgumentException("Match id must be set");
		}

		if (playerId != null) {
			map.put("playerId", playerId);
		} else {
			throw new IllegalArgumentException("Player id must be set");
		}

		map.put("roleId", roleId);

		map.put("teamId", teamId);

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
			matchPlayer.getRole() != null ? matchPlayer.getRole().getId() : null,
			matchPlayer.getTeam() != null ? matchPlayer.getTeam().getId() : null,
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

		if (document.contains("teamId")) {
			firebaseMatchPlayer.setTeamId(document.getString("teamId"));
		}

		if (document.contains("win")) {
			firebaseMatchPlayer.setWin(document.getBoolean("win"));
		}

		return firebaseMatchPlayer;
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

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
}
