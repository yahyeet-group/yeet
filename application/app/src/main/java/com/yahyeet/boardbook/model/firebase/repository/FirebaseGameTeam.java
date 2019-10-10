package com.yahyeet.boardbook.model.firebase.repository;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.yahyeet.boardbook.model.entity.GameTeam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class FirebaseGameTeam {

    private String name;
    private List<FirebaseGameRole> gameRoles;

    public FirebaseGameTeam(String name, List<FirebaseGameRole> gameRoles) {
        this.name = name;
        this.gameRoles = gameRoles;
    }

    public Map<String, Object> toMap(){
        Map<String, Object> map = new HashMap<>();

        if (name != null) {
            map.put("name", name);
        }

        map.put("roles", gameRoles.stream().map(FirebaseGameRole::toMap));

        return map;
    }


    public static FirebaseGameTeam fromGameTeam(GameTeam gameTeam){
        return new FirebaseGameTeam(
                gameTeam.getName(),
                gameTeam.getRoleList()
                        .stream()
                        .map(FirebaseGameRole::fromGameRole)
                        .collect(Collectors.toList())
        );
    }

    public GameTeam toGameTeam(){
        return new GameTeam(
                name,
                gameRoles
                    .stream()
                    .map(FirebaseGameRole::toGameRole)
                    .collect(Collectors.toList())
        );
    }
}
