package com.yahyeet.boardbook.model.handlers;

import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.model.entity.GameRole;
import com.yahyeet.boardbook.model.entity.GameTeam;
import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.model.entity.MatchPlayer;
import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.handler.GameHandler;
import com.yahyeet.boardbook.model.handler.MatchHandler;
import com.yahyeet.boardbook.model.handler.UserHandler;
import com.yahyeet.boardbook.mock.repository.MockGameRepository;
import com.yahyeet.boardbook.mock.repository.MockGameRoleRepository;
import com.yahyeet.boardbook.mock.repository.MockGameTeamRepository;
import com.yahyeet.boardbook.mock.repository.MockMatchPlayerRepository;
import com.yahyeet.boardbook.mock.repository.MockMatchRepository;
import com.yahyeet.boardbook.mock.repository.MockUserRepository;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class UserHandlerTest {

	private UserHandler userHandler;
	private MatchHandler matchHandler;
	private GameHandler gameHandler;


	private MockUserRepository userRepository;
	private MockMatchRepository matchRepository;
	private MockGameRoleRepository roleRepository;
	private MockGameTeamRepository teamRepository;
	private MockGameRepository gameRepository;
	private MockMatchPlayerRepository playerRepository;

	@Before
	public void initTests() {
		userRepository = new MockUserRepository();
		matchRepository = new MockMatchRepository();
		roleRepository = new MockGameRoleRepository();
		teamRepository = new MockGameTeamRepository();
		gameRepository = new MockGameRepository();
		playerRepository = new MockMatchPlayerRepository();

		userHandler = new UserHandler(
			userRepository,
			matchRepository,
			roleRepository,
			teamRepository,
			gameRepository,
			playerRepository
		);

		matchHandler = new MatchHandler(
			matchRepository,
			playerRepository,
			gameRepository,
			roleRepository,
			teamRepository,
			userRepository
		);

		gameHandler = new GameHandler(
			gameRepository,
			roleRepository,
			teamRepository,
			matchRepository
		);
	}

	@Test
	public void saveUserAndGet() {
		User user = new User("Carl");
		try {
			String id = userHandler.save(user).get().getId();
			User result = userHandler.find(id).get();
			assertEquals(user, result);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getAllTest() {
		User user1 = new User("Carlos");
		User user2 = new User("Alexanderos");
		User user3 = new User("Aaronos");
		User user4 = new User("Dinos");
		User user5 = new User("Rasmusos");

		try {
			userHandler.save(user1).get();
			userHandler.save(user2).get();
			userHandler.save(user3).get();
			userHandler.save(user4).get();
			userHandler.save(user5).get();
			int size = userHandler.all().get().size();
			assertEquals(5, size);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void saveUserWithFriendAndGet() {
		User user1 = new User("Carl");
		user1.setId("carlId");
		User user2 = new User("Rasmus");
		user2.setId("rasmusId");
		user1.addFriend(user2);
		try {
			String id = userHandler.save(user1).get().getId();
			User result = userHandler.find(id, UserHandler.generatePopulatorConfig(true, false)).get();
			assertEquals(user1, result);
			assertEquals(1, result.getFriends().size());
			assertEquals(user2, result.getFriends().get(0));
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void saveUserWithMatchPlayerAndGet() {
		Game game = new Game(
			"Avalon",
			"Cool game where you laugh when someone accuses you of being Oberon",
			4,
			5,
			10
		);
		GameTeam gt = new GameTeam("Team name");
		gt.addRole(new GameRole("Role name"));

		game.addTeam(gt);
		try {
			game = gameHandler.save(game, GameHandler.generatePopulatorConfig(false, true)).get();
		} catch (ExecutionException | InterruptedException e) {
			e.printStackTrace();
		}

		Match match = new Match(game);
		User user = new User();
		try {
			user = userHandler.save(user).get();
		} catch (ExecutionException | InterruptedException e) {
			e.printStackTrace();
		}

		GameTeam gameTeam = game.getTeams().get(0);
		GameRole gameRole = game.getTeams().get(0).getRoles().get(0);


		MatchPlayer player = new MatchPlayer(user, gameRole, gameTeam, false);

		try {

			match.addMatchPlayer(player);
			matchHandler.save(match).get();
			String id = userHandler.save(user).get().getId();
			User result = userHandler.find(user.getId()).get();
			assertEquals(user, result);
		} catch (ExecutionException | InterruptedException e) {
			e.printStackTrace();
			fail();
		}
	}
}