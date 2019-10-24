package com.yahyeet.boardbook.model.entities;

import com.yahyeet.boardbook.model.entity.User;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserEntityTest {
	User user;
	User friend;

	@Before
	public void initTests() {
		user = new User("testUser");
		user.setId("testUserId");
		friend = new User("friendUser");
		friend.setId("friendId");
	}

	@Test
	public void addFriendsTest() {
		assertEquals(0, user.getFriends().size());
		user.addFriend(friend);
		assertEquals(1, user.getFriends().size());
		assertEquals("friendUser", user.getFriends().get(0).getName());
	}

	@Test
	public void youAreAddedAsFriendTest() {
		assertEquals(0, friend.getFriends().size());
		user.addFriend(friend);
		assertEquals(1, friend.getFriends().size());
		assertEquals("testUser", friend.getFriends().get(0).getName());
	}

	@Test
	public void addFriendMultipleTimesTest() {
		assertEquals(0, user.getFriends().size());
		user.addFriend(friend);
		user.addFriend(friend);
		user.addFriend(friend);
		user.addFriend(friend);
		user.addFriend(friend);
		assertEquals(1, user.getFriends().size());
		assertEquals("friendUser", user.getFriends().get(0).getName());
	}

	@Test
	public void addItselfAsFriend() {
		assertEquals(0, user.getFriends().size());
		user.addFriend(user);
		assertEquals(0, user.getFriends().size());
	}
}
