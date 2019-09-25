package com.yahyeet.boardbook.model.mock.service;

import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.service.IAuthService;

import java.util.List;
import java.util.concurrent.CompletableFuture;

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
            return null;
        });
    }

    @Override
    public CompletableFuture<Void> logout() {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<User> signup(String email, String password, String name) {
        return CompletableFuture.supplyAsync(() -> {
            mockUserDb.add(new AuthenticationUser(email, password, new User("", name)));

            for(AuthenticationUser mockUser : mockUserDb){
                if(mockUser.email.equals(email) && mockUser.password.equals(password)){
                    return mockUser.user;
                }
            }
            return null;
        });

    }

    public class AuthenticationUser {
        public String email;
        public String password;
        public User user;

        public AuthenticationUser(String email, String password, User user) {
            this.email = email;
            this.password = password;
            this.user = user;
        }
    }
}
