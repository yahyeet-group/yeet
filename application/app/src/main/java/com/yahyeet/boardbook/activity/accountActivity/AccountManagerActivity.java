package com.yahyeet.boardbook.activity.accountActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.activity.HomeActivity;
import com.yahyeet.boardbook.model.util.LoginFailedException;
import com.yahyeet.boardbook.presenter.AccountManagerPresenter;

public class AccountManagerActivity extends AppCompatActivity implements IAccountManager {

	private AccountManagerPresenter accountManagerPresenter;
	private LoginFragment loginFragment;
	private RegisterFragment registerFragment;
	private Button registerSwitchButton;

	/**
	 * Calls super and the initiates presenter, fragments and xml items.
	 *
	 * @param savedInstanceState sent to super
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_manager);

		accountManagerPresenter = new AccountManagerPresenter(this);
		registerSwitchButton = findViewById(R.id.registerSwitchButton);

		registerSwitchButton.setOnClickListener(view -> showRegisterFragment());

		loginFragment = new LoginFragment();
		registerFragment = new RegisterFragment();


		getSupportFragmentManager().beginTransaction().replace(R.id.accountManagerContainer, loginFragment, "Login").commit();
	}

	/**
	 * Method tries to log into app given valid parameters
	 *
	 * @param email    of the account trying to log in
	 * @param password of the account trying to log in
	 */
	public void loginAccount(String email, String password) {
		try {
			accountManagerPresenter.loginAccount(email, password);
		} catch (LoginFailedException e) {
			//TODO: Implement better exception handling
			loginFragment.showErrorMessage();
		}
	}

	/**
	 * Method tries to create a new account from parameters and log in with it
	 *
	 * @param email    of the new account
	 * @param password of the new account
	 * @param username of the new account
	 */
	public void registerAccount(String email, String password, String username) {
		accountManagerPresenter.registerAccount(email, password, username);
	}

	/**
	 * Starts the homepage and ends the activity manager.
	 */
	@Override
	public void finishAccountManager() {
		Intent startHome = new Intent(this, HomeActivity.class);
		startActivity(startHome);
		finish();
	}

	@Override
	public void onBackPressed() {
		if (registerFragment.equals(getSupportFragmentManager().findFragmentByTag("Register")))
			showLoginFragment();
		else
			super.onBackPressed();
	}

	/**
	 * Changes the currently visible fragment to the Login Fragment
	 */
	private void showLoginFragment() {
		registerSwitchButton.setVisibility(View.VISIBLE);
		getSupportFragmentManager().beginTransaction().replace(R.id.accountManagerContainer, loginFragment, "Login").commit();
	}

	/**
	 * Changes the currently visible fragment to the Register Fragment
	 */
	private void showRegisterFragment() {
		registerSwitchButton.setVisibility(View.INVISIBLE);
		getSupportFragmentManager().beginTransaction().replace(R.id.accountManagerContainer, registerFragment, "Register").commit();
	}

	/**
	 * Enables all interactive elements of the activity
	 */
	@Override
	public void enableManagerInteraction() {

		enableFragment();
		registerSwitchButton.setEnabled(true);
		findViewById(R.id.accountLoadingLayout).setVisibility(View.INVISIBLE);
	}

	/**
	 * Disables all interactive elements of the activity
	 */
	@Override
	public void disableManagerInteraction() {
		disableFragment();
		registerSwitchButton.setEnabled(false);
		findViewById(R.id.accountLoadingLayout).setVisibility(View.VISIBLE);
	}

	/**
	 * Enables the currently active fragment
	 */
	private void enableFragment() {
		if (registerFragment.equals(getSupportFragmentManager().findFragmentByTag("Register")))
			registerFragment.setFragmentInteraction(true);
		else {
			loginFragment.setFragmentInteraction(true);
		}
	}

	/**
	 * Disables the currently active fragment
	 */
	private void disableFragment() {
		if (registerFragment.equals(getSupportFragmentManager().findFragmentByTag("Register")))
			registerFragment.setFragmentInteraction(false);
		else {
			loginFragment.setFragmentInteraction(false);
		}
	}


}
