package com.yahyeet.boardbook.presenter;


import com.yahyeet.boardbook.activity.ILoginActivity;
import com.yahyeet.boardbook.activity.LoginActivity;
import com.yahyeet.boardbook.activity.LoginFragment;

public class LoginPresenter {


    private ILoginActivity loginActivity;


    public LoginPresenter(ILoginActivity loginActivity) {

        this.loginActivity = loginActivity;
    }


    public void login(String email, String password) {
        BoardbookSingleton.getInstance().getAuthHandler().login(email, password).thenAccept(u -> {
            // access logged in user from "u"
        }).exceptionally(e -> {
            // Handle error ("e")
            // TODO: Make presenter tell view to act upon different exceptions
            loginActivity.showErrorMessage();
            e.printStackTrace();
            return null;
        });
    }

        public void signup(String email, String password, String username) {
            BoardbookSingleton.getInstance().getAuthHandler().signup(email, password, username).thenAccept(u -> {
                // access logged in user from "u"
                //registerActivity.finish();
            }).exceptionally(e -> {
                // Handle error ("e")
                // TODO: Make presenter tell view to act upon different exceptions
                e.printStackTrace();
                return null;
            });
        }

    }
