package com.yahyeet.boardbook.presenter;

import com.yahyeet.boardbook.activity.IRegisterActivity;

public class RegisterPresenter {


    private IRegisterActivity registerActivity;

    public RegisterPresenter(IRegisterActivity registerActivity){
        this.registerActivity = registerActivity;
    }


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
