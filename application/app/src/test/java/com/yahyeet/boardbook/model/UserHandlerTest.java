package com.yahyeet.boardbook.model;

import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.model.entity.GameRole;
import com.yahyeet.boardbook.model.entity.GameTeam;
import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.handler.GameHandler;
import com.yahyeet.boardbook.model.handler.UserHandler;
import com.yahyeet.boardbook.model.mock.repository.MockGameRepository;
import com.yahyeet.boardbook.model.mock.repository.MockGameRoleRepository;
import com.yahyeet.boardbook.model.mock.repository.MockGameTeamRepository;
import com.yahyeet.boardbook.model.mock.repository.MockMatchPlayerRepository;
import com.yahyeet.boardbook.model.mock.repository.MockMatchRepository;
import com.yahyeet.boardbook.model.mock.repository.MockUserRepository;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class UserHandlerTest {

    private UserHandler userHandler;

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
    }

    @Test
    public void saveUserAndGet(){
        User user = new User("Carl");
        try{
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
}