package com.yahyeet.boardbook;

import com.yahyeet.boardbook.model.Boardbook;
import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.mock.service.MockAuthService;
import com.yahyeet.boardbook.model.mock.service.MockGameRepository;
import com.yahyeet.boardbook.model.mock.service.MockMatchRepository;
import com.yahyeet.boardbook.model.mock.service.MockUserRepository;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

public class FirebaseMockTest {


	MockAuthService authService;
	MockUserRepository userRepository;
	MockGameRepository gameRepository;
	MockMatchRepository matchRepository;

	Boardbook boardbook;


	@Before
	public void initTests(){
		 userRepository = new MockUserRepository();
		 authService = new MockAuthService(new ArrayList<>());
		 gameRepository = new MockGameRepository();
		 matchRepository = new MockMatchRepository(userRepository);

		boardbook = new Boardbook(authService, userRepository, gameRepository, matchRepository);

	}


	// This test works in its completableFutureness, write tests like this
	@Test
	public void testLogin() throws ExecutionException, InterruptedException {
		User user = new User("TestUser");

		User friend = new User("Friendly");
		Match match = new Match();

		user.getFriends().add(friend);
		user.getMatches().add(match);


		MockAuthService.AuthenticationUser authUser = new MockAuthService.AuthenticationUser("email@email.test", "wordpass", user);

		boardbook.getAuthHandler().signup(authUser.getEmail(), authUser.getPassword(), authUser.getUser().getName()).thenAccept(user1 -> {
			assertEquals(user.getName(), boardbook.getAuthHandler().getLoggedInUser().getName());
			//TODO: Add more assert equals, id?
		}).get();

	}

}
