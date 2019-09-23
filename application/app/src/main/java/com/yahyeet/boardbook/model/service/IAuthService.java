package com.yahyeet.boardbook.model.service;

import com.yahyeet.boardbook.model.entity.User;

public interface IAuthService {
    User login(String email, String password) throws Exception;

    void logout();

    void signup(String email, String password, String name) throws Exception;
}
