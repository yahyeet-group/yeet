package com.yahyeet.boardbook.model.handlers;

import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.model.entity.GameRole;
import com.yahyeet.boardbook.model.entity.GameTeam;
import com.yahyeet.boardbook.model.handler.GameHandler;
import com.yahyeet.boardbook.model.mock.repository.MockGameRepository;
import com.yahyeet.boardbook.model.mock.repository.MockGameRoleRepository;
import com.yahyeet.boardbook.model.mock.repository.MockGameTeamRepository;
import com.yahyeet.boardbook.model.mock.repository.MockMatchRepository;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

public class GameHandlerTest {

    private GameHandler gameHandler;

    @Before
    public void initTests() {
        gameHandler = new GameHandler(
                new MockGameRepository(),
                new MockGameRoleRepository(),
                new MockGameTeamRepository(),
                new MockMatchRepository()
        );
    }

    @Test
    public void gaveAndGetGame() {
        Game game = new Game(
                "Avalon",
                "Cool game where you laugh when someone accuses you of being Oberon",
                4,
                5,
                10
        );

        try {
            String id = gameHandler.save(game).get().getId();
            Game result = gameHandler.find(id).get();
            assertEquals(game, result);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            fail();
        }

    }

    @Test
    public void getAllTest() {
        Game game1 = new Game(
                "Avalon",
                "Cool game where you laugh when someone accuses you of being Oberon",
                4,
                5,
                10
        );

        Game game2 = new Game(
                "Coup",
                "I am a Duke",
                2,
                3,
                6
        );

        Game game3 = new Game(
                "Skull",
                "Do i have a flower or a skull. That is the question",
                1,
                2,
                8
        );

        try {
            gameHandler.save(game1).get();
            gameHandler.save(game2).get();
            gameHandler.save(game3).get();
            int size = gameHandler.all().get().size();
            assertEquals(3, size);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void saveGameWithRolesAndTeam() {
        Game game = new Game(
                "Avalon",
                "Cool game where you laugh when someone accuses you of being Oberon",
                4,
                5,
                10
        );
        GameTeam team = new GameTeam();
        team.setName("Good");
        GameRole role = new GameRole();
        role.setName("Merlin");
        team.addRole(role);
        game.addTeam(team);

        try {
            String id = gameHandler.save(game).get().getId();
            Game result = gameHandler.find(id).get();
            assertEquals(game, result);
        }
        catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            fail();
        }
    }


}