package com.yahyeet.boardbook;

import com.yahyeet.boardbook.model.Boardbook;
import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.handler.AuthHandler;
import com.yahyeet.boardbook.model.handler.GameHandler;
import com.yahyeet.boardbook.model.handler.MatchHandler;
import com.yahyeet.boardbook.model.handler.UserHandler;
import com.yahyeet.boardbook.model.mock.repository.MockGameRoleRepository;
import com.yahyeet.boardbook.model.mock.repository.MockGameTeamRepository;
import com.yahyeet.boardbook.model.mock.repository.MockMatchPlayerRepository;
import com.yahyeet.boardbook.model.mock.service.MockAuthService;
import com.yahyeet.boardbook.model.mock.repository.MockGameRepository;
import com.yahyeet.boardbook.model.mock.repository.MockMatchRepository;
import com.yahyeet.boardbook.model.mock.repository.MockUserRepository;

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
    MockGameTeamRepository teamRepository;
    MockMatchPlayerRepository playerRepository;
    MockGameRoleRepository roleRepository;

    Boardbook boardbook;


    @Before
    public void initTests() {
        authService = new MockAuthService(new ArrayList<>());

        userRepository = new MockUserRepository();
        gameRepository = new MockGameRepository();
        matchRepository = new MockMatchRepository();
        teamRepository = new MockGameTeamRepository();
        playerRepository = new MockMatchPlayerRepository();
        roleRepository = new MockGameRoleRepository();


        boardbook = new Boardbook(
                new AuthHandler(authService),
                new UserHandler(userRepository, matchRepository, roleRepository, teamRepository,gameRepository, playerRepository),
                new GameHandler(gameRepository, roleRepository, teamRepository, matchRepository),
                new MatchHandler(matchRepository, playerRepository, gameRepository, roleRepository, teamRepository, userRepository)
        );

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
