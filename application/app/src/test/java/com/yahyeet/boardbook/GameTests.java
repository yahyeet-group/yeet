package com.yahyeet.boardbook;

import com.yahyeet.boardbook.model.entity.Game;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GameTests {

    public GameTests(){}

    //TODO Write test for seeing if name,desc.... is correct, doesn't seem very important


    /// See if you can add a team
    @Test
    public void addTeam(){
        Game game = getTestGame();
        game.addTeam("SOM");
        assertEquals(game.getAllTeamNames().get(0), "SOM");
        game.addTeam("LSOA");
        assertEquals(game.getAllTeamNames().get(1), "LSOA");

    }

    /// See if you can add roles to a team
    @Test
    public void addRole(){
        Game game = getTestGame();
        game.addTeam("LSOA");
        game.addRole("LSOA", "Merlin");
        assertEquals(game.getAllRoles("LSOA").get(0), "Merlin");

        game.addTeam("SOM");
        game.addRole("SOM", "Mordred");
        assertEquals(game.getAllRoles("SOM").get(0), "Mordred");

        game.addRole("SOM", "Morgana");
        assertEquals(game.getAllRoles("SOM"), new ArrayList<String>(){{add("Mordred"); add("Morgana");}});
    }




    private Game getTestGame(){
        return new Game("Avalon", "Fun Game", "Descriptive Description");
    }

}

