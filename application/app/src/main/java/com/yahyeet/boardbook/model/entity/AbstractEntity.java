package com.yahyeet.boardbook.model.entity;

public abstract class AbstractEntity {
    private String id;

    public AbstractEntity(String id) {
        this.id = id;
    }

    public AbstractEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
