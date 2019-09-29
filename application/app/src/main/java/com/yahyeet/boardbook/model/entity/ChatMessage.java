package com.yahyeet.boardbook.model.entity;

import java.util.Date;

public class ChatMessage extends Entity {
    public ChatMessage(String id) {
        super(id);
    }

    private User sender;
    private String content;
    private Date createdAt;
    //TODO Should the message really have a reference to which group it belongs to?? Shouldn't the group know what messages
    // it owns?

    public ChatMessage(User sender,String content ) {
        this.sender = sender;
        this.content = content;
        this.createdAt = new Date();
    }

    public ChatMessage(){}; //TODO Check if the Firebase implementation needs to be changed or this one needs to

    public User getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    //TODO broadcast the edit
    public String editContent(String content){
        this.content = content;
        return content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }


}
