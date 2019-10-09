package com.yahyeet.boardbook.presenter;

import com.yahyeet.boardbook.activity.accountActivity.IAccountManager;

public class AccountManagerPresenter {

    private IAccountManager accountManagerActivity;

    public AccountManagerPresenter(IAccountManager accountManagerActivity){
        this.accountManagerActivity = accountManagerActivity;
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

    private void finishAccountManager(){
        accountManagerActivity.finishAccountManager();
    }
}
