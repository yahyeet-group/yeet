package com.yahyeet.boardbook.model.firebase.repository;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.yahyeet.boardbook.model.entity.GameTeam;

import java.io.Serializable;
import java.security.cert.CollectionCertStoreParameters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class FirebaseGameTeam implements Serializable {

	private String name;
	private List<FirebaseGameRole> roles;

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

		map.put("roles", roles.stream().map(FirebaseGameRole::toMap).collect(Collectors.toList()));

		return map;
	}


	static FirebaseGameTeam fromGameTeam(GameTeam gameTeam) {
		FirebaseGameTeam firebaseGameTeam = new FirebaseGameTeam(gameTeam.getName());

		firebaseGameTeam.setRoles(
			gameTeam.getRoles()
				.stream()
				.map(FirebaseGameRole::fromGameRole)
				.collect(Collectors.toList())
		);

		return firebaseGameTeam;
	}

	GameTeam toGameTeam() {
		GameTeam gameTeam = new GameTeam(name);

		gameTeam.setRoles(
			roles
				.stream()
				.map(FirebaseGameRole::toGameRole)
				.collect(Collectors.toList()));

		return gameTeam;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<FirebaseGameRole> getRoles() {
		return roles;
	}

	public void setRoles(List<FirebaseGameRole> roles) {
		this.roles = roles;
	}
}
