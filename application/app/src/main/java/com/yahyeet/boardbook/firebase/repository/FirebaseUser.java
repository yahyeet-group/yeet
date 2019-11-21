package com.yahyeet.boardbook.firebase.repository;

import com.google.firebase.firestore.DocumentSnapshot;
import com.yahyeet.boardbook.model.entity.User;

import java.util.HashMap;
import java.util.Map;

class FirebaseUser extends AbstractFirebaseEntity<User> {
	private String name;

	public FirebaseUser() {
	}

	public FirebaseUser(String id, String name) {
		super(id);
		this.name = name;
	}

	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> data = new HashMap<>();

		if (name != null) {
			data.put("name", name);
		}

		return data;
	}

	@Override
	public User toModelType() {
		User user = new User(name);
		user.setId(getId());
		return user;
	}

	/**
	 * Creates a Firebase user entity from a model user
	 *
	 * @param user Model user
	 * @return Firebase user
	 */
	public static FirebaseUser fromModelType(User user) {
		return new FirebaseUser(user.getId(), user.getName());
	}

	/**
	 * Create a Firebase user from a Firebase document snapshot
	 *
	 * @param document Firebase document snapshot
	 * @return Firebase user
	 */
	public static FirebaseUser fromDocument(DocumentSnapshot document) {
		FirebaseUser firebaseUser = new FirebaseUser();
		firebaseUser.setId(document.getId());

		if (document.contains("name")) {
			firebaseUser.setName(document.getString("name"));
		}

		return firebaseUser;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
