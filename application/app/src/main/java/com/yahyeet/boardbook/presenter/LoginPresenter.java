package com.yahyeet.boardbook.presenter;


import com.yahyeet.boardbook.activity.ILoginActivity;
import com.yahyeet.boardbook.activity.LoginActivity;

public class LoginPresenter {


    private ILoginActivity loginActivity;


    public LoginPresenter(LoginActivity loginActivity){
        this.loginActivity = loginActivity;
    }


    public void login(String email, String password) {
        BoardbookSingleton.getInstance().getAuthHandler().login(email, password).thenAccept(u -> {
            // access logged in user from "u"
            loginActivity.finish();
        }).exceptionally(e -> {
            // Handle error ("e")
            // TODO: Make presenter tell view to act upon different exceptions
            loginActivity.showErrorMessage();
            e.printStackTrace();
            return null;
        });

    }
}
