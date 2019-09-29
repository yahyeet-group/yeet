package com.yahyeet.boardbook.model.entity;

import java.util.ArrayList;
import java.util.List;

public class ChatGroup extends Entity {
    public ChatGroup(String id) {
        super(id);
    }

    private List<User> users = new ArrayList<>();
    private List<ChatMessage> messages = new ArrayList<>();
    private String groupName = "New Chat Group";

    //TODO ChaT Group must contain at least two users to instantiate?
    public ChatGroup() {
    }

    /// Messages
    public void addMessage(ChatMessage message){
        messages.add(message);
    }

    public List<ChatMessage> getAllMessages(){
        return messages;
    }


    /// Users
    public void addUser(User u){
        users.add(u);
    }

    public List<User> getUsers(){
        return users;
    }

    public void removeUsers(User u){
        users.remove(u);
    }

    /// Group Name

    public void setGroupName(String name){
        groupName = name;
    }

    public String getGroupName(){
        return groupName;
    }


}
