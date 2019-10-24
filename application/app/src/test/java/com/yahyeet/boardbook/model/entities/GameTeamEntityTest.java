package com.yahyeet.boardbook.model.entities;

import com.yahyeet.boardbook.model.entity.GameRole;
import com.yahyeet.boardbook.model.entity.GameTeam;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GameTeamEntityTest {
	GameTeam team;

	@Before
	public void initTests() {
		team = new GameTeam("Good guys");
	}

	@Test
	public void addRoleTest() {
		GameRole role = new GameRole("Merlin");
		assertEquals(0, team.getRoles().size());
		team.addRole(role);
		assertEquals(1, team.getRoles().size());
		assertEquals("Merlin", team.getRoles().get(0).getName());
	}
}
