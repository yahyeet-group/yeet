package com.yahyeet.boardbook;

import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.handler.AuthHandler;
import com.yahyeet.boardbook.model.handler.UserHandler;
import com.yahyeet.boardbook.model.mock.repository.MockUserRepository;
import com.yahyeet.boardbook.model.mock.service.MockAuthService;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AuthenticationTest {
    @Test
    public void login() {
        List<MockAuthService.AuthenticationUser> authenticationUsers = new ArrayList<>();
        authenticationUsers.add(new MockAuthService.AuthenticationUser("test@test.test", "MyPassword", new User("id", "Testificate")));
        MockAuthService mockAuthService = new MockAuthService(authenticationUsers);
        UserHandler userHandler = new UserHandler(new MockUserRepository());
        AuthHandler authHandler = new AuthHandler(mockAuthService, userHandler);

        mockAuthService.login("test@test.test", "MyPassword").thenAccept(user -> {
            Assert.assertEquals("Testificate", user.getName());
            Assert.assertEquals("id", user.getId());
            Assert.assertEquals("Testificate", authHandler.getLoggedInUser().getName());
            Assert.assertEquals("test@test.test",user.getEmail());
        }).exceptionally(e -> {
            Assert.fail();
            return null;
        });
    }

    @Test
    public void register() {
        List<MockAuthService.AuthenticationUser> authenticationUsers = new ArrayList<>();
        MockAuthService mockAuthService = new MockAuthService(authenticationUsers);
        UserHandler userHandler = new UserHandler(new MockUserRepository());
        AuthHandler authHandler = new AuthHandler(mockAuthService, userHandler);

        mockAuthService.signup("prov@prov.prov","MinKod","Testare");

        Assert.assertNotEquals(0,authenticationUsers.size());
        Assert.assertEquals("Testare",authenticationUsers.get(0).user.getName());
        Assert.assertEquals("MinKod",authenticationUsers.get(0).password);
        Assert.assertEquals("prov@prov.prov",authenticationUsers.get(0).user.getEmail());

    }

    @Test
    public void logout() {
        List<MockAuthService.AuthenticationUser> authenticationUsers = new ArrayList<>();
        MockAuthService mockAuthService = new MockAuthService(authenticationUsers);
        UserHandler userHandler = new UserHandler(new MockUserRepository());
        AuthHandler authHandler = new AuthHandler(mockAuthService, userHandler);

        authHandler.setLoggedInUser(new User());
        Assert.assertNotEquals(null , authHandler.getLoggedInUser());
        authHandler.logout();
        Assert.assertEquals(null, authHandler.getLoggedInUser());



    }
}
