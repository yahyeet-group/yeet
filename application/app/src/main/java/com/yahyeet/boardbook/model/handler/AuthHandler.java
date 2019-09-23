package com.yahyeet.boardbook.model.handler;

import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.service.IAuthService;

public class AuthHandler {

    private IAuthService authService;

    public AuthHandler(IAuthService authService){
        this.authService = authService;
    }

    User login(String email, String password) throws Exception{
        return authService.login(email, password);
    }

    void logout(){
        authService.logout();
    }

    void signup(String email, String password, String name) throws Exception{
        authService.signup(email, password, name);
    }
}
