package com.yahyeet.boardbook.authentication;

import com.yahyeet.boardbook.entity.User;

public interface IAuthService {
    User logIn(String email, String password);

    void logOut(User user);

    void register(String email, String password, String name);
}
