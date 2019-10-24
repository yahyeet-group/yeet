package com.yahyeet.boardbook.model.entities;

import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.model.entity.GameTeam;
import com.yahyeet.boardbook.model.entity.Match;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GameEntityTest {
	Game game;

	@Before
	public void initTests() {
		game = new Game("Avalon", "Cool game");
	}

	@Test
	public void addTeamTest() {
		GameTeam team = new GameTeam("Good");
		assertEquals(0, game.getTeams().size());
		game.addTeam(team);
		assertEquals(1, game.getTeams().size());
		assertEquals("Good", game.getTeams().get(0).getName());
	}

	@Test
	public void addMatchTest() {
		Match match = new Match("id");
		assertEquals(0, game.getMatches().size());
		game.addMatch(match);
		assertEquals(1, game.getMatches().size());
		assertEquals("id", game.getMatches().get(0).getId());
	}
}
