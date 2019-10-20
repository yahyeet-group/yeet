package com.yahyeet.boardbook.model;

import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.model.entity.GameRole;
import com.yahyeet.boardbook.model.entity.GameTeam;
import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.model.entity.MatchPlayer;
import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.handler.MatchHandler;
import com.yahyeet.boardbook.model.handler.UserHandler;
import com.yahyeet.boardbook.model.mock.repository.MockGameRepository;
import com.yahyeet.boardbook.model.mock.repository.MockGameRoleRepository;
import com.yahyeet.boardbook.model.mock.repository.MockGameTeamRepository;
import com.yahyeet.boardbook.model.mock.repository.MockMatchPlayerRepository;
import com.yahyeet.boardbook.model.mock.repository.MockMatchRepository;
import com.yahyeet.boardbook.model.mock.repository.MockUserRepository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class MatchHandlerTest {

    private MatchHandler matchHandler;
    private UserHandler userHandler;

    private MockMatchRepository matchRepository;
    private MockMatchPlayerRepository playerRepository;
    private MockGameRepository gameRepository;
    private MockGameRoleRepository roleRepository;
    private MockGameTeamRepository teamRepository;
    private MockUserRepository userRepository;


    @Before
    public void initTests() {
        matchRepository =  new MockMatchRepository();
        playerRepository = new MockMatchPlayerRepository();
        gameRepository = new MockGameRepository();
        roleRepository = new MockGameRoleRepository();
        teamRepository = new MockGameTeamRepository();
        userRepository = new MockUserRepository();

        matchHandler = new MatchHandler(
                matchRepository,
                playerRepository,
                gameRepository,
                roleRepository,
                teamRepository,
                userRepository
        );
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
    public void saveAndGetMatch() {
        Game game = new Game(
                "Avalon",
                "Cool game where you laugh when someone accuses you of being Oberon",
                4,
                5,
                10
        );

        game.setId("0");

        Match match = new Match(game);
        User user = new User();
        user.setId("0");
        try {
            userHandler.save(user).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        GameRole gameRole = new GameRole();
        gameRole.setId("0");
        GameTeam gameTeam = new GameTeam();
        gameTeam.setId("0");

        User userPlayer = null;
        try {
            userPlayer = userHandler.find(user.getId()).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        MatchPlayer player = new MatchPlayer(userPlayer, gameRole, gameTeam, false);

        try {

            match.addMatchPlayer(player);
            Match m = matchHandler.save(match).get();
            String id = m.getId();
            Match result = matchHandler.find(id).get();
            assertEquals(match, result);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void getAllTest() {
        Game game = new Game(
                "Avalon",
                "Cool game where you laugh when someone accuses you of being Oberon",
                4,
                5,
                10
        );

        game.setId("0");

        User user = new User();
        userHandler.save(user);

        GameRole gameRole = new GameRole();
        gameRole.setId("0");
        GameTeam gameTeam = new GameTeam();
        gameTeam.setId("0");
        User userPlayer = null;
        try {
            userPlayer = userHandler.find(user.getId()).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        MatchPlayer player = new MatchPlayer(userPlayer, gameRole, gameTeam, false);

        Match match1 = new Match(game);
        match1.addMatchPlayer(player);
        Match match2 = new Match(game);
        match2.addMatchPlayer(player);
        Match match3 = new Match(game);
        match3.addMatchPlayer(player);

        try {
            matchHandler.save(match1).get();
            matchHandler.save(match2).get();
            matchHandler.save(match3).get();
            int size = matchHandler.all().get().size();
            assertEquals(3, size);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void addMatchWithGameWithoutId(){
        Game game = new Game(
                "Avalon",
                "Cool game where you laugh when someone accuses you of being Oberon",
                4,
                5,
                10
        );

        Match match = new Match(game);

        try {
            Match m = matchHandler.save(match).get();
        } catch (ExecutionException | InterruptedException ignored) {
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void addMatchWithGameWithoutPlayers(){
        Game game = new Game(
                "Avalon",
                "Cool game where you laugh when someone accuses you of being Oberon",
                4,
                5,
                10
        );
        game.setId("0");

        Match match = new Match(game);

        try {
            Match m = matchHandler.save(match).get();
        } catch (ExecutionException | InterruptedException ignored) {
        }
    }
}