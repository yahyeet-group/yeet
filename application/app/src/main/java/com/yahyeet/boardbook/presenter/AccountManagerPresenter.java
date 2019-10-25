package com.yahyeet.boardbook.presenter;

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.yahyeet.boardbook.activity.account.IAccountManagerActivity;

import android.os.Looper;

import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.util.EmailFailedException;
import com.yahyeet.boardbook.model.util.PasswordFailedException;

/**
 * Presenter for the account manager activity
 */
public class AccountManagerPresenter {
	private IAccountManagerActivity accountManagerActivity;
	// Set true to skip login
	private Boolean fastPass = false;

	public AccountManagerPresenter(IAccountManagerActivity accountManagerActivity) {
		this.accountManagerActivity = accountManagerActivity;
		if (fastPass) {
			BoardbookSingleton.getInstance().getAuthHandler().setLoggedInUser(new User("The Almighty Temp User"));
			finishAccountManager();
		}
	}

	public void loginAccount(String email, String password) throws EmailFailedException, PasswordFailedException {

		if (email.isEmpty()) {
			throw new EmailFailedException("Email can not be empty.");
		} else if (emailInvalid(email)) {
			throw new EmailFailedException("Email is not in a correct format");
		}

		if (passwordInvalid(password)) {
			throw new PasswordFailedException("Password needs to be longer than 6 characters");
		}

		accountManagerActivity.disableManagerInteraction();
		BoardbookSingleton.getInstance().getAuthHandler().login(email, password).thenAccept(u -> {

			finishAccountManager();

		}).exceptionally(e -> {

			e.printStackTrace();
			new android.os.Handler(Looper.getMainLooper()).post(() -> {
				accountManagerActivity.enableManagerInteraction();
				//TODO: Weird getCause calls
				if (e.getCause().getCause() instanceof FirebaseAuthInvalidCredentialsException) {
					accountManagerActivity.loginFailed(new Exception("Incorrect email or password"));
				} else
					accountManagerActivity.loginFailed(new Exception("Account not found"));


			});
			return null;
		});

	}

	public void registerAccount(String email, String password, String username) throws EmailFailedException, PasswordFailedException {

		if (email.isEmpty()) {
			throw new EmailFailedException("Email can not be empty.");
		} else if (emailInvalid(email)) {
			throw new EmailFailedException("Email is not in a correct format");
		}

		if (passwordInvalid(password)) {
			throw new PasswordFailedException("Password needs to be longer than 6 characters");
		}


		accountManagerActivity.disableManagerInteraction();
		BoardbookSingleton.getInstance().getAuthHandler().signup(email, password, username).thenAccept(u -> {
			// access logged in user from "u"
			finishAccountManager();
		}).exceptionally(e -> {
			// Handle error ("e")
			// TODO: Make presenter tell view to act upon different exceptions
			new android.os.Handler(Looper.getMainLooper()).post(() -> {
				accountManagerActivity.enableManagerInteraction();
				if (e.getCause().getCause() instanceof FirebaseAuthUserCollisionException) {
					accountManagerActivity.registerFailed(new EmailFailedException(e.getCause().getCause().getMessage()));
					//TODO: Hardcoded knowing it will go 2 ways deep, fix in repositories
				}

			});
			e.printStackTrace();
			return null;
		});
	}

	private void finishAccountManager() {
		accountManagerActivity.finishAccountManager();
	}

	/**
	 * Checks if email matches android pattern for valid email
	 *
	 * @param email gets compared against android email pattern
	 * @return if email is valid or not
	 */
	private boolean emailInvalid(CharSequence email) {
		return !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
	}

	/**
	 * Checks if password is valid or not
	 *
	 * @param password to be validated
	 * @return if password is valid or not
	 */
	private boolean passwordInvalid(CharSequence password) {
		return password.length() < 6;
	}
}
