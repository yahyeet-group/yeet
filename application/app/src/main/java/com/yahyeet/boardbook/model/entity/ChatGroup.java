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

    // Tried to add a message, if the user is in the group it gets added and returns true, if not it returns false
    public boolean addMessage(ChatMessage message){
        if(users.contains(message.getSender())){
            messages.add(message);
            return true;
        }else {return false;}

    }

    public List<ChatMessage> getAllMessages(){
        return messages;
    }

    //TODO this should maybe return an "This message has been removed message"
    public void removeMessage(ChatMessage message){
        messages.remove(message);
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
