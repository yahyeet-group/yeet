package com.yahyeet.boardbook;

import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.mock.service.MockAuthService;
import com.yahyeet.boardbook.model.mock.service.MockGameRepository;
import com.yahyeet.boardbook.model.mock.service.MockMatchRepository;
import com.yahyeet.boardbook.model.mock.service.MockUserRepository;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class FirebaseMockTest {


	MockAuthService authService;
	MockUserRepository userRepository;
	MockGameRepository gameRepository;
	MockMatchRepository matchRepository;


	@Before
	public void initTests(){
		 userRepository = new MockUserRepository();
		 authService = new MockAuthService(new ArrayList<>());
		 gameRepository = new MockGameRepository();
		 matchRepository = new MockMatchRepository(userRepository);

	}


	// This test works in its completableFutureness, write tests like this
	@Test
	public void testLogin(){
		User user = new User("TestUser");
		MockAuthService.AuthenticationUser authUser = new MockAuthService.AuthenticationUser("email@email.test", "wordpass", user);

		authService.signup(authUser.getEmail(), authUser.getPassword(), authUser.getUser().getName()).thenAccept(user1 -> {
			assertEquals(user.getName(), user1.getName());
			//TODO: Add more assert equals, id?
		});

	}

}
