package com.yahyeet.boardbook.model.firebase.repository;

import com.yahyeet.boardbook.model.entity.GameRole;
import com.yahyeet.boardbook.model.entity.GameTeam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseGameRole {

    private String name;

    public FirebaseGameRole() {
    }

    FirebaseGameRole(String name) {
        this.name = name;
    }


    static FirebaseGameRole fromGameRole(GameRole gameRole) {
        return new FirebaseGameRole(gameRole.getName());
    }

    GameRole toGameRole(){
        return new GameRole(name);
    }


    Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();

        if (name != null) {
            map.put("name", name);
        }

        return map;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
