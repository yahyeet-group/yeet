package com.yahyeet.boardbook.activity;

import android.view.View;

import com.yahyeet.boardbook.presenter.LoginPresenter;

public interface ILoginActivity {

    LoginPresenter getLoginPresenter();

    void setLoginPresenter(LoginPresenter loginPresenter);

    void loginAccount(View view);


    void showErrorMessage();

    void hideErrorMessage();

}
