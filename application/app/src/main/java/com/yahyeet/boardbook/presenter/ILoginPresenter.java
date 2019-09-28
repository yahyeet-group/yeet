package com.yahyeet.boardbook.presenter;

import com.yahyeet.boardbook.activity.LoginActivity;

public interface ILoginPresenter {

    void login(String email, String password);

    LoginActivity getLoginActivity();

    void setLoginActivity(LoginActivity loginActivity);

}
