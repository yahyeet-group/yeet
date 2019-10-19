package com.yahyeet.boardbook.model;

import com.yahyeet.boardbook.model.entity.Game;
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
    public void SaveAndGetGame(){
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
            assertEquals(result, game);
        } catch (ExecutionException | InterruptedException e) {
            fail();
        }

    }
}