package com.yahyeet.boardbook.model.firebase.repository;

import com.yahyeet.boardbook.model.entity.GameRole;
import com.yahyeet.boardbook.model.entity.GameTeam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseGameRole {

    private String id;
    private String name;
    private String teamId;

    public FirebaseGameRole() {
    }

    public FirebaseGameRole(String id, String name, String teamId) {
        this.id = id;
        this.name = name;
        this.teamId = teamId;
    }

    static FirebaseGameRole fromGameRole(GameRole gameRole) {
        return new FirebaseGameRole(gameRole.getId(), gameRole.getName(), gameRole.getTeam().getId());
    }

    GameRole toGameRole(){
        GameRole gameRole = new GameRole(name);
        gameRole.setId(id);
        return gameRole;
    }


    Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();

        if (name != null) {
            map.put("name", name);
        }

        if (teamId != null) {
            map.put("teamId", teamId);
        }

        return map;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }
}
