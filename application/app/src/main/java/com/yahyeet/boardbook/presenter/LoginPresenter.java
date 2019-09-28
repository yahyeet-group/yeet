package com.yahyeet.boardbook.presenter;

import android.app.ProgressDialog;

import com.yahyeet.boardbook.activity.BoardbookSingleton;
import com.yahyeet.boardbook.activity.LoginActivity;

public class LoginPresenter implements ILoginPresenter {


    private LoginActivity loginActivity;
    private ProgressDialog progress;


    public LoginPresenter(LoginActivity loginActivity){
        this.loginActivity = loginActivity;
    }

    @Override
    public void login(String email, String password) {

        startLoginLoading();

        BoardbookSingleton.getInstance().getAuthHandler().login(email, password).thenAccept(u -> {
            // access logged in user from "u"
            endLoginLoading();
            loginActivity.finish();

        }).exceptionally(e -> {
            // Handle error ("e")

            // TODO: Make presenter tell view to act upon different exceptions
            loginActivity.showErrorMessage();
            endLoginLoading();

            e.printStackTrace();

            return null;
        });

    }

    void startLoginLoading(){
        progress = new ProgressDialog(loginActivity);
        // TODO: Make better loading text
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
    }


    void endLoginLoading(){
        progress.dismiss();
    }

    @Override
    public LoginActivity getLoginActivity() {
        return loginActivity;
    }

    @Override
    public void setLoginActivity(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
    }
}
