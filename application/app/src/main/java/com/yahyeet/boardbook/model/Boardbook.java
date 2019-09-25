package com.yahyeet.boardbook.model;

import com.yahyeet.boardbook.model.handler.AuthHandler;
import com.yahyeet.boardbook.model.service.IAuthService;

public final class Boardbook {

    private AuthHandler authHandler;

    private Boardbook(IAuthService authService) {
        authHandler = new AuthHandler(authService);
    }
}