package com.yahyeet.boardbook.model.entity;

import android.app.slice.Slice;
import android.media.Image;

import java.util.ArrayList;
import java.util.List;

public class Game extends Entity {
    public Game(String id) {
        super(id);
    }

    private String name;
    private List<List<String>> teamSetup = new ArrayList<>();
    private Image logo; // TODO This must be looked into more before an implementation is written
    private String description;
    private String rules;


    // When loading one from Firebase where the handler parses the teamsetup
    public Game(String name, String description, String rules, List<List<String>> teamSetup) {
        this.name = name;
        this.description = description;
        this.rules = rules;
        this.teamSetup = teamSetup;
    }

    // When creating a New one without any rules
    public Game(String name, String description, String rules) {
        this.name = name;
        this.description = description;
        this.rules = rules;
        this.teamSetup = teamSetup;
    }

    public Game(){}; //TODO Here there must also be a discussion of how to implement towards Firebase

    //TODO if I implement an edit here how to we update it in Firebase aswell.

    public String getName() {
        return name;
    }

    public Image getLogo() {
        return logo;
    }

    public String getDescription() {
        return description;
    }

    public String getRules() {
        return rules;
    }

    //TODO This must be discussed how it is supposed to work
    public List<?> getTeamSetup() {
        return teamSetup;
    }

    /// Current implementation will be that all teams first element will be team name and remaining Strings will be roles
    public void addTeam(String teamName){
        List<String> team = new ArrayList<>();
        team.add(teamName);
        teamSetup.add(team);

    }


    /// Adds one role for the specified team
    public void addRole(String teamName, String roleName){
        for (int i = 0; i< teamSetup.size(); i++ ){
            if(teamSetup.get(i).get(0) == teamName){
                teamSetup.get(i).add(roleName);
                break;
            }
        }
   }
    /// Adds and entire list of roles to one team
    public void addRole(String teamName, List<String> roleNames){
        for (int i = 0; i< teamSetup.size(); i++ ){
            if(teamSetup.get(i).get(0) == teamName){
                List<String> subsetTS = teamSetup.get(i);
               roleNames.forEach((n)-> subsetTS.add(n) );
               break;
            }
        }
    }

   /// Returns a list with all team NAMES. Not the entire team array with all roles but only the names of them all
    // I.E the first element of every array.
   public List<String> getAllTeamNames(){
       List<String> teams = new ArrayList<>();
       for (int i = 0; i< teamSetup.size(); i++ ){
           teams.add(teamSetup.get(i).get(0));
       }
       return teams;
   }


    // Returns a list of all roles for a specific team name
   public List<String> getAllRoles(String teamName){
       for (int i = 0; i< teamSetup.size(); i++ ){
           if(teamSetup.get(i).get(0) == teamName){
               List<String> roles = new ArrayList<>(teamSetup.get(i));
               roles.remove(0);
               return roles;
           }
       }
       return null;
   }


}


