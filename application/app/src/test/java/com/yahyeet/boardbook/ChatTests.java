package com.yahyeet.boardbook;

import com.yahyeet.boardbook.model.entity.ChatGroup;
import com.yahyeet.boardbook.model.entity.User;

import org.junit.Test;
import java.util.ArrayList;
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
        assertEquals(cg.getUsers().get(0), user1);
    }

    // Checks if specific users can be removed
    @Test
    public void removeUserTest(){
        ChatGroup cg = getTestGroup();
        cg.removeUsers(user1);
        assertEquals(cg.getUsers().get(0), user2);
    }



    /////////////// NAME TESTS

    // Check the default name(Necessary?)
    @Test
    public void defaultNameTest(){
        ChatGroup cg = getTestGroup();

        assertEquals(cg.getGroupName(), "New Chat Group");
    }

    // Check name change
    @Test
    public void customGroupNameTest(){
        ChatGroup cg = getTestGroup();
        cg.setGroupName("Cuzt0m");
        assertEquals(cg.getGroupName(), "Cuzt0m");
    }

    // Helper Method
    private ChatGroup getTestGroup(){
        ChatGroup cg = new ChatGroup();
        cg.addUser(user1);
        cg.addUser(user2);
        return cg;
    }

}
