package com.yahyeet.boardbook.model.entities;

import com.yahyeet.boardbook.model.entity.GameRole;
import com.yahyeet.boardbook.model.entity.GameTeam;
import com.yahyeet.boardbook.model.entity.MatchPlayer;
import com.yahyeet.boardbook.model.entity.User;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MatchPlayerEntityTest {

	@Test
	public void idConstructorTest() {
		MatchPlayer player = new MatchPlayer("cool_stuffs");
		assertEquals("cool_stuffs", player.getId());
	}

	@Test
	public void userConstructorTest() {
		User user = new User("user");
		user.setId("id");
		GameRole role = new GameRole("role");
		GameTeam team = new GameTeam("team");
		MatchPlayer player = new MatchPlayer(user, role, team, true);
		assertEquals("user", player.getUser().getName());
		assertEquals("role", player.getRole().getName());
		assertEquals("team", player.getTeam().getName());
	}
}
