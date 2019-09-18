package com.yahyeet.boardbook.model.service;

import com.yahyeet.boardbook.model.entity.User;

public interface IAuthService {
    User logIn(String email, String password) throws Exception;

    void logOut();

    void register(String email, String password, String name) throws Exception;
}
