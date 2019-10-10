package com.yahyeet.boardbook.model.firebase.repository;

import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.model.entity.GameRole;
import com.yahyeet.boardbook.model.entity.GameTeam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FirebaseGame {

    private String id;
    private String name;
    private String description;
    private String rules;

    private int difficulty;
    private int minPlayers;
    private int maxPlayers;

    private List<FirebaseGameTeam> teams;

    public FirebaseGame() {}

    public FirebaseGame(String name, String description, String rules,
                        int difficulty, int minPlayers, int maxPlayers,
                        List<FirebaseGameTeam> teams ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.rules = rules;
        this.difficulty = difficulty;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.teams = teams;

    }

    public static FirebaseGame fromGame(Game game){
        return new FirebaseGame(
                game.getName(),
                game.getDescription(),
                game.getRules(),
                game.getDifficulty(),
                game.getMinPlayers(),
                game.getMaxPlayers(),
                game.getTeams()
                .stream()
                .map(FirebaseGameTeam::fromGameTeam)
                .collect(Collectors.toList())
        );
    }

    public static Game toGame(FirebaseGame firebaseGame){
       return new Game(
                firebaseGame.getId(),
                firebaseGame.getName(),
                firebaseGame.getDescription(),
                firebaseGame.getRules(),
                firebaseGame.getDifficulty(),
                firebaseGame.getMinPlayers(),
                firebaseGame.getMaxPlayers(),
                firebaseGame.getTeams()
                        .stream()
                        .map(FirebaseGameTeam::toGameTeam)
                        .collect(Collectors.toList())
        );
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        if (name != null) {
            map.put("name", name);
        }

        if (description != null) {
            map.put("description", description);
        }

        if (rules != null) {
            map.put("rules", rules);
        }

        map.put("difficulty", difficulty);


        map.put("minplayers", minPlayers);


        map.put("maxplayer", maxPlayers);

        map.put("teams", teams.stream().map(FirebaseGameTeam::toMap).collect(Collectors.toList()));

        return map;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public List<FirebaseGameTeam> getTeams() {
        return teams;
    }

    public void setTeams(List<FirebaseGameTeam> teams) {
        this.teams = teams;
    }
}
