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
	private List<String> roles;

	public FirebaseGameTeam() {
	}

	FirebaseGameTeam(String name) {
		this.name = name;
		this.roles = new ArrayList<>();
	}

	Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<>();

		if (name != null) {
			map.put("name", name);
		}

		if (roles != null) {
			map.put("roles", roles);
		}

		return map;
	}


	static FirebaseGameTeam fromGameTeam(GameTeam gameTeam) {
		FirebaseGameTeam firebaseGameTeam = new FirebaseGameTeam(gameTeam.getName());

		firebaseGameTeam.setRoles(
			gameTeam
				.getRoles()
				.stream()
				.map(AbstractEntity::getId)
				.collect(Collectors.toList())
		);

		return firebaseGameTeam;
	}

	GameTeam toGameTeam() {
		return new GameTeam(name);
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

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
}
