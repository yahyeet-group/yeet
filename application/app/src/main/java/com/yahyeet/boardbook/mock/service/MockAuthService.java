package com.yahyeet.boardbook.mock.service;

import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.service.IAuthService;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

/**
 * A mock implementation of the authentication service
 */
public class MockAuthService implements IAuthService {

    private List<AuthenticationUser> mockUserDb;

    public MockAuthService(List<AuthenticationUser> db){
        this.mockUserDb = db;
    }

    @Override
    public CompletableFuture<User> login(String email, String password) {
        return CompletableFuture.supplyAsync(() -> {
            for(AuthenticationUser mockUser : mockUserDb){
                if(mockUser.email.equals(email) && mockUser.password.equals(password)){
                    return mockUser.user;
                }
            }

            throw new CompletionException(new Exception("User not found"));
        });
    }

    @Override
    public CompletableFuture<Void> logout() {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<User> signup(String email, String password, String name) {
        return CompletableFuture.supplyAsync(() -> {
            User user = new User(name);
            AuthenticationUser authUser = new AuthenticationUser(email, password, user);
            mockUserDb.add(authUser);

            return user;
        });

    }

    public static class AuthenticationUser {
        public String email;
        public String password;
        public User user;

        public AuthenticationUser(String email, String password, User user) {
            this.email = email;
            this.password = password;
            this.user = user;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }

        public User getUser() {
            return user;
        }

        public List<User> getFriends() {
            return user.getFriends();
        }

        public List<Match> getMatches() {
            return user.getMatches();
        }
    }
}
