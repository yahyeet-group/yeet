package com.yahyeet.boardbook;

import com.yahyeet.boardbook.model.entity.ChatGroup;
import com.yahyeet.boardbook.model.entity.ChatMessage;
import com.yahyeet.boardbook.model.entity.User;

import org.junit.Test;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class ChatTests {

    private User user1 = new User("123", "Nox");
    private User user2 = new User("321", "Emia");
    private List<User> users = new ArrayList<>();


    public ChatTests(){
        users.add(user1);
        users.add(user2);
        System.out.println();
    }


    //////////////// USER TESTS

    // Checks if users are in group / If users can be added
    @Test
    public void chatGroupTest(){
        ChatGroup cg = getTestGroup();
        assertEquals( user1, cg.getUsers().get(0));
    }

    // Checks if specific users can be removed
    @Test
    public void removeUserTest(){
        ChatGroup cg = getTestGroup();
        cg.removeUsers(user1);
        assertEquals( user2, cg.getUsers().get(0));
    }



    /////////////// NAME TESTS

    // Check the default name(Necessary?)
    @Test
    public void defaultNameTest(){
        ChatGroup cg = getTestGroup();

        assertEquals("New Chat Group", cg.getGroupName());
    }

    // Check name change
    @Test
    public void customGroupNameTest(){
        ChatGroup cg = getTestGroup();
        cg.setGroupName("Cuzt0m");
        assertEquals( "Cuzt0m", cg.getGroupName());
    }


    /////////// MESSAGES TESTS

    // Check if you can add a message
    @Test
    public void addMessageTest(){
        ChatGroup cg = getTestGroup();
        cg.addMessage(getTestMessage(user1));

        assertEquals( 1, cg.getAllMessages().size());
    }

    // Check if a message can be removed
    @Test
    public void removeMessageTest(){
        ChatGroup cg = getTestGroup();
        cg.addMessage(getTestMessage(user1));
        cg.removeMessage(cg.getAllMessages().get(0));

        assertEquals( 0, cg.getAllMessages().size());
    }

    // Check if a message contains the right message, both default and custom
    @Test
    public void messageContentTest(){
        ChatGroup cg = getTestGroup();
        cg.addMessage(getTestMessage(user1));
        assertEquals( "Test", cg.getAllMessages().get(0).getContent());

        cg.getAllMessages().get(0).editContent("Edited");
        assertEquals( "Edited", cg.getAllMessages().get(0).getContent());

    }

    // Check if the sender is correct(unecessary?)
    @Test
    public void checkSenderTest(){
        ChatGroup cg = getTestGroup();
        cg.addMessage(getTestMessage(user1));
        assertEquals( user1, cg.getAllMessages().get(0).getSender());
    }

    // Check the right time(This is hella wonk though)
    @Test
    public void checkTime(){
        ChatGroup cg = getTestGroup();
        cg.addMessage(getTestMessage(user1));
        assertEquals(( (int)(new Date().getTime() * 0.00001)), (int)(cg.getAllMessages().get(0).getCreatedAt().getTime() * 0.00001));
    }




    ////// Helper Methods
    private ChatGroup getTestGroup(){
        ChatGroup cg = new ChatGroup();
        cg.addUser(user1);
        cg.addUser(user2);
        return cg;
    }

    private ChatMessage getTestMessage(User u){
        return new ChatMessage(u, "Test");
    }




}
