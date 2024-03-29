package com.yahyeet.boardbook.firebase.repository;

import com.google.firebase.firestore.DocumentSnapshot;
import com.yahyeet.boardbook.model.entity.GameRole;
import com.yahyeet.boardbook.model.entity.GameTeam;

import java.util.HashMap;
import java.util.Map;

public class FirebaseGameRole extends AbstractFirebaseEntity<GameRole> {
	private String name;
	private String teamId;

	public FirebaseGameRole() {
	}

	public FirebaseGameRole(String id, String name, String teamId) {
		super(id);
		this.name = name;
		this.teamId = teamId;
	}

	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<>();

		if (name != null) {
			map.put("name", name);
		}

		if (teamId != null) {
			map.put("teamId", teamId);
		}

		return map;
	}

	@Override
	public GameRole toModelType() {
		GameRole gameRole = new GameRole(name);
		gameRole.setId(getId());
		GameTeam gameTeam = new GameTeam();
		gameTeam.setId(teamId);
		gameRole.setTeam(gameTeam);
		return gameRole;
	}

	/**
	 * Creates a Firebase game role entity from a model game role
	 *
	 * @param gameRole Model game role
	 * @return Firebase game role
	 */
	public static FirebaseGameRole fromModelType(GameRole gameRole) {
		return new FirebaseGameRole(gameRole.getId(), gameRole.getName(), gameRole.getTeam().getId());
	}

	/**
	 * Create a Firebase game role from a Firebase document snapshot
	 *
	 * @param document Firebase document snapshot
	 * @return Firebase game role
	 */
	public static FirebaseGameRole fromDocument(DocumentSnapshot document) {
		FirebaseGameRole firebaseGameRole = new FirebaseGameRole();
		firebaseGameRole.setId(document.getId());

		if (document.contains("name")) {
			firebaseGameRole.setName(document.getString("name"));
		}

		if (document.contains("teamId")) {
			firebaseGameRole.setTeamId(document.getString("teamId"));
		}

		return firebaseGameRole;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
}
