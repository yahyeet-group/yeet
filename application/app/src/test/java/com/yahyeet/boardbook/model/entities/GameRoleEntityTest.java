package com.yahyeet.boardbook.model.entities;

import com.yahyeet.boardbook.model.entity.GameRole;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GameRoleEntityTest {
	GameRole role;

	@Before
	public void initTests() {
		role = new GameRole("Merlin");
	}

	@Test
	public void constructorTest() {
		assertEquals("Merlin", role.getName());
	}
}
