package com.yahyeet.boardbook.model.firebase.repository;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.yahyeet.boardbook.model.entity.GameTeam;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class FirebaseGameTeam implements Serializable {

    private String name;
    private List<FirebaseGameRole> gameRoles;

    public FirebaseGameTeam() {
    }

    FirebaseGameTeam(String name, List<FirebaseGameRole> gameRoles) {
        this.name = name;
        this.gameRoles = gameRoles;
    }

    Map<String, Object> toMap(){
        Map<String, Object> map = new HashMap<>();

        if (name != null) {
            map.put("name", name);
        }

        map.put("roles", gameRoles.stream().map(FirebaseGameRole::toMap));

        return map;
    }


    static FirebaseGameTeam fromGameTeam(GameTeam gameTeam){
        return new FirebaseGameTeam(
                gameTeam.getName(),
                gameTeam.getRoleList()
                        .stream()
                        .map(FirebaseGameRole::fromGameRole)
                        .collect(Collectors.toList())
        );
    }

    GameTeam toGameTeam(){
        return new GameTeam(
                name,
                gameRoles
                    .stream()
                    .map(FirebaseGameRole::toGameRole)
                    .collect(Collectors.toList())
        );
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FirebaseGameRole> getGameRoles() {
        return gameRoles;
    }

    public void setGameRoles(List<FirebaseGameRole> gameRoles) {
        this.gameRoles = gameRoles;
    }
}
