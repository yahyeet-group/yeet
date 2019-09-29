package com.yahyeet.boardbook.model.entity;

import java.util.Date;

public class ChatMessage extends Entity {
    public ChatMessage(String id) {
        super(id);
    }

    private User sender;
    private String content;
    private Date date;

    public ChatMessage() {
    }
}
