package com.yahyeet.boardbook;

import com.yahyeet.boardbook.model.entity.Game;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GameTests {

    public GameTests(){}

    //TODO Write test for seeing if name,desc.... are correct, doesn't seem very important


    /// See if you can add a team
    @Test
    public void addTeam(){
        Game game = getTestGame();
        game.addTeam("SOM");
        assertEquals( "SOM", game.getAllTeamNames().get(0));
        game.addTeam("LSOA");
        assertEquals( "LSOA", game.getAllTeamNames().get(1));

    }

    /// See if you can add roles to a team
    @Test
    public void addRole(){
        Game game = getTestGame();
        game.addTeam("LSOA");
        game.addRole("LSOA", "Merlin");
        assertEquals( "Merlin", game.getAllRoles("LSOA").get(0));

        game.addTeam("SOM");
        game.addRole("SOM", "Mordred");
        assertEquals( "Mordred", game.getAllRoles("SOM").get(0));

        game.addRole("SOM", "Morgana");
        assertEquals( new ArrayList<String>(){{add("Mordred"); add("Morgana");}}, game.getAllRoles("SOM"));
    }




    private Game getTestGame(){
        return new Game("Avalon", "Fun Game", "Descriptive Description");
    }

}

