package com.yahyeet.boardbook.model.entities;

import com.yahyeet.boardbook.model.entity.GameRole;
import com.yahyeet.boardbook.model.entity.GameTeam;
import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.model.entity.MatchPlayer;
import com.yahyeet.boardbook.model.entity.User;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MatchEntityTest {
	Match match;

	@Before
	public void initTests() {
		match = new Match("id");
	}

	@Test
	public void addMatchPlayerTest() {
		MatchPlayer player = new MatchPlayer("coolioPlayer");
		assertEquals(0, match.getMatchPlayers().size());
		match.addMatchPlayer(player);
		assertEquals(1, match.getMatchPlayers().size());
		assertEquals("coolioPlayer", match.getMatchPlayers().get(0).getId());
	}

	@Test
	public void getMatchPlayerByUserTest() {
		User user1 = new User("user1");
		user1.setId("id1");
		User user2 = new User("user2");
		user2.setId("id2");

		GameRole role = new GameRole();

		GameTeam team = new GameTeam();

		MatchPlayer player1 = new MatchPlayer(user1, role, team, true);
		MatchPlayer player2 = new MatchPlayer(user2, role, team, false);

		match.addMatchPlayer(player1);
		match.addMatchPlayer(player2);
		assertFalse(match.getMatchPlayerByUser(user2).getWin());
		assertTrue(match.getMatchPlayerByUser(user1).getWin());
	}
}
