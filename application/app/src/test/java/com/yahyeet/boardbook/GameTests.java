package com.yahyeet.boardbook;

import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.model.entity.GameRole;
import com.yahyeet.boardbook.model.entity.GameTeam;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameTests {

    public GameTests(){}

    /// See if you can add a team
    @Test
    public void addTeam(){
        Game game = getTestGame();
        game.getTeams().add(new GameTeam("SOM"));
        assertEquals( "SOM", game.getTeams().get(0).getName());
        game.getTeams().add(new GameTeam("LSOA"));
        assertEquals( "LSOA", game.getTeams().get(1).getName());
    }

    /// See if you can add roles to a team
    @Test
    public void addRole(){
        Game game = getTestGame();

        GameTeam lsoaTeam = new GameTeam("LSOA");
        lsoaTeam.getRoles().add(new GameRole("Merlin"));
        game.getTeams().add(lsoaTeam);

        assertEquals( "Merlin", game.getTeams().get(0).getRoles().get(0).getName());

        GameTeam somTeam = new GameTeam("SOM");
        somTeam.getRoles().add(new GameRole("Mordred"));
        game.getTeams().add(somTeam);
        assertEquals( "Mordred", game.getTeams().get(1).getRoles().get(0).getName());
    }

    private Game getTestGame(){
        return new Game("Avalon", "Descriptive Description", 3, 2, 7);
    }

}

