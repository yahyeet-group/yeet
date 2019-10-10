package com.yahyeet.boardbook.model.entity;

import java.util.List;

public class GameTeam extends AbstractEntity {
    private String name;
    private List<GameRole> roleList;


    public GameTeam(String name, List<GameRole> rolelist) {
        this.name = name;
        this.roleList = rolelist;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<GameRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<GameRole> roleList) {
        this.roleList = roleList;
    }
}
