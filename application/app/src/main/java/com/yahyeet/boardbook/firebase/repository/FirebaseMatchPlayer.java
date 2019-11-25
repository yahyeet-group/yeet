package com.yahyeet.boardbook.firebase.repository;

import com.google.firebase.firestore.DocumentSnapshot;
import com.yahyeet.boardbook.model.entity.GameRole;
import com.yahyeet.boardbook.model.entity.GameTeam;
import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.model.entity.MatchPlayer;
import com.yahyeet.boardbook.model.entity.User;

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
		MatchPlayer matchPlayer = new MatchPlayer(getId());
		matchPlayer.setMatch(new Match(matchId));

		User user = new User();
		user.setId(playerId);
		matchPlayer.setUser(user);

		if (teamId != null) {
			GameTeam gameTeam = new GameTeam();
			gameTeam.setId(teamId);
			matchPlayer.setTeam(gameTeam);
		}

		if (roleId != null) {
			GameRole gameRole = new GameRole();
			gameRole.setId(roleId);
			matchPlayer.setRole(gameRole);
		}

		matchPlayer.setWin(win);

		return matchPlayer;
	}

	/**
	 * Creates a Firebase match player entity from a model match player
	 *
	 * @param matchPlayer Model match player
	 * @return Firebase match player
	 */
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

	/**
	 * Create a Firebase match player from a Firebase document snapshot
	 *
	 * @param document Firebase document snapshot
	 * @return Firebase match player
	 */
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
