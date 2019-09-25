package com.yahyeet.boardbook.model.handler;

import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.service.IAuthService;

import java.util.concurrent.CompletableFuture;

public class AuthHandler {

    private IAuthService authService;

    public AuthHandler(IAuthService authService){
        this.authService = authService;
    }

    CompletableFuture<User> login(String email, String password) throws Exception{
        return authService.login(email, password).thenApply((s) -> {
            return new User("hs", "hs");
        });
    }

    void logout(){
        authService.logout();
    }

    void signup(String email, String password, String name) throws Exception{
        authService.signup(email, password, name);
    }
}
