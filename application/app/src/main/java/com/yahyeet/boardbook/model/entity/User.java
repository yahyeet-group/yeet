package com.yahyeet.boardbook.model.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Defines a user entity
 */
public class User extends AbstractEntity {
	private String name;
	private List<User> friends = new ArrayList<>();
	private List<Match> matches = new ArrayList<>();

	public User(String name) {
		this.name = name;
	}

	public User() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<User> getFriends() {
		return friends;
	}

	/**
	 * Adds a user to the current users friends list, and also adds the current user to the target
	 * users friends list
	 *
	 * @param friend
	 */
	public void addFriend(User friend) {
		if (friend.equals(this) || getFriends().stream().anyMatch(f -> f.equals(friend))) {
			return;
		}
		friends.add(friend);
		friend.addFriend(this);
	}

	public List<Match> getMatches() {
		return matches;
	}

	public void addMatch(Match match) {
		matches.add(match);
	}
}
