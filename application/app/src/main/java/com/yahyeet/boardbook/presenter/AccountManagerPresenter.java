package com.yahyeet.boardbook.presenter;

import android.content.Intent;

import com.yahyeet.boardbook.activity.HomeActivity;
import com.yahyeet.boardbook.activity.accountActivity.AccountManagerActivity;
import com.yahyeet.boardbook.model.entity.User;

public class AccountManagerPresenter {

    private AccountManagerActivity accountManagerActivity;

    // Set true to skip login
    private Boolean fastPass = true;

    public AccountManagerPresenter(AccountManagerActivity accountManagerActivity){
        this.accountManagerActivity = accountManagerActivity;
        if(fastPass){
            BoardbookSingleton.getInstance().getAuthHandler().setLoggedInUser(new User("The Almighty Temp User"));
            finishAccountManager();
        }
    }

    public void loginAccount(String email, String password) {

        BoardbookSingleton.getInstance().getAuthHandler().login(email, password).thenAccept(u -> {
            // access logged in user from "u"
            finishAccountManager();
        }).exceptionally(e -> {
            // Handle error ("e")
            // TODO: Make presenter tell view to act upon different exceptions
            e.printStackTrace();
            return null;
        });

    }

    public void registerAccount(String email, String password, String username) {
        BoardbookSingleton.getInstance().getAuthHandler().signup(email, password, username).thenAccept(u -> {
            // access logged in user from "u"
            finishAccountManager();
        }).exceptionally(e -> {
            // Handle error ("e")
            // TODO: Make presenter tell view to act upon different exceptions
            e.printStackTrace();
            return null;
        });
    }

    // TODO: This really needs to be interface abstracted
    private void finishAccountManager(){
        Intent startHome = new Intent(accountManagerActivity, HomeActivity.class);
        accountManagerActivity.startActivity(startHome);
    }
}
