package com.yahyeet.boardbook.model.handler;

import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.service.IAuthService;

public class AuthHandler {
    
    private IAuthService authService;

    public AuthHandler(IAuthService authService){
        this.authService = authService;
    }

    User logIn(String email, String password) throws Exception{
        return authService.logIn(email, password);
    }

    void logOut(){
        authService.logOut();
    }

    void register(String email, String password, String name) throws Exception{
        authService.register(email, password, name);
    }
}
