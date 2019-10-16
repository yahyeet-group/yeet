package com.yahyeet.boardbook.presenter;

import com.yahyeet.boardbook.activity.accountActivity.IAccountManager;

import android.content.Intent;

import com.yahyeet.boardbook.activity.HomeActivity;
import com.yahyeet.boardbook.activity.accountActivity.AccountManagerActivity;
import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.util.LoginFailedException;

public class AccountManagerPresenter {

<<<<<<< HEAD
    private IAccountManager accountManagerActivity;

    // Set true to skip login
    private Boolean fastPass = true;

    public AccountManagerPresenter(IAccountManager accountManagerActivity){
        this.accountManagerActivity = accountManagerActivity;
        if(fastPass){
            BoardbookSingleton.getInstance().getAuthHandler().setLoggedInUser(new User("The Almighty Temp User"));
            finishAccountManager();
        }
    }

    public void loginAccount(String email, String password) {
        accountManagerActivity.disableManagerInteraction();
        BoardbookSingleton.getInstance().getAuthHandler().login(email, password).thenAccept(u -> {
            // access logged in user from "u"
            finishAccountManager();
        }).exceptionally(e -> {
            // Handle error ("e")
            // TODO: Make presenter tell view to act upon different exceptions
            e.printStackTrace();
            accountManagerActivity.enableManagerInteraction();
            return null;
        });

    }

    public void registerAccount(String email, String password, String username) {
        accountManagerActivity.disableManagerInteraction();
        BoardbookSingleton.getInstance().getAuthHandler().signup(email, password, username).thenAccept(u -> {
            // access logged in user from "u"
            finishAccountManager();
        }).exceptionally(e -> {
            // Handle error ("e")
            // TODO: Make presenter tell view to act upon different exceptions
            accountManagerActivity.enableManagerInteraction();
            e.printStackTrace();
            return null;
        });
    }

    private void finishAccountManager(){
        accountManagerActivity.finishAccountManager();
    }
=======
	private IAccountManager accountManagerActivity;

	// Set true to skip login
	private Boolean fastPass = false;

	public AccountManagerPresenter(IAccountManager accountManagerActivity) {
		this.accountManagerActivity = accountManagerActivity;
		if (fastPass) {
			BoardbookSingleton.getInstance().getAuthHandler().setLoggedInUser(new User("The Almighty Temp User"));
			finishAccountManager();
		}
	}

	public void loginAccount(String email, String password) throws LoginFailedException {

		if (password.length() <= 6) {
			throw new LoginFailedException("Password needs to be longer than 6 characters");
		}

		accountManagerActivity.disableManagerInteraction();
		BoardbookSingleton.getInstance().getAuthHandler().login(email, password).thenAccept(u -> {
			// access logged in user from "u"
			finishAccountManager();
		}).exceptionally(e -> {
			// Handle error ("e")
			// TODO: Make presenter tell view to act upon different exceptions
			e.printStackTrace();
			accountManagerActivity.enableManagerInteraction();
			return null;
		});

	}

	public void registerAccount(String email, String password, String username) {
		accountManagerActivity.disableManagerInteraction();
		BoardbookSingleton.getInstance().getAuthHandler().signup(email, password, username).thenAccept(u -> {
			// access logged in user from "u"
			finishAccountManager();
		}).exceptionally(e -> {
			// Handle error ("e")
			// TODO: Make presenter tell view to act upon different exceptions
			accountManagerActivity.enableManagerInteraction();
			e.printStackTrace();
			return null;
		});
	}

	private void finishAccountManager() {
		accountManagerActivity.finishAccountManager();
	}
>>>>>>> develop
}
