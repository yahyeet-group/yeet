package com.yahyeet.boardbook.presenter;

import com.yahyeet.boardbook.activity.RegisterActivity;

public class RegisterPresenter implements IRegisterPresenter {


    private RegisterActivity registerActivity;

    public RegisterPresenter(RegisterActivity registerActivity){
        this.registerActivity = registerActivity;
    }

    @Override
    public void signup(String email, String password, String username) {

        BoardbookSingleton.getInstance().getAuthHandler().signup(email, password, username).thenAccept(u -> {
            // access logged in user from "u"
            registerActivity.finish();

        }).exceptionally(e -> {
            // Handle error ("e")

            // TODO: Make presenter tell view to act upon different exceptions
            e.printStackTrace();

            return null;
        });

    }

}
