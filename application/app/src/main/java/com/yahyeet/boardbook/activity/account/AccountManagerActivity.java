package com.yahyeet.boardbook.activity.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.activity.home.HomeActivity;
import com.yahyeet.boardbook.model.util.EmailFailedException;
import com.yahyeet.boardbook.model.util.PasswordFailedException;
import com.yahyeet.boardbook.presenter.AccountManagerPresenter;

/**
 * Activity that holds the account fragments 
 */
public class AccountManagerActivity extends AppCompatActivity implements IAccountManagerActivity, IAccountFragmentHolder {

	private AccountManagerPresenter accountManagerPresenter;
	private LoginFragment loginFragment;
	private RegisterFragment registerFragment;
	private Button registerSwitchButton;

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


	@Override
	public void loginAccount(String email, String password) {
		try {
			loginFragment.displayLoginError(false);
			accountManagerPresenter.loginAccount(email, password);
		} catch (EmailFailedException | PasswordFailedException e) {
			loginFailed(e);
		}
	}


	@Override
	public void registerAccount(String email, String password, String username) {
		try {
			accountManagerPresenter.registerAccount(email, password, username);
		} catch (EmailFailedException | PasswordFailedException e){
			registerFailed(e);
		}
	}


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


	@Override
	public void loginFailed(Exception exception){
		if(exception instanceof EmailFailedException)
			loginFragment.emailFailed(exception.getMessage());
		else if(exception instanceof PasswordFailedException)
			loginFragment.passwordFailed(exception.getMessage());
		else{
			loginFragment.displayLoginError(exception);
		}
	}


	@Override
	public void registerFailed(Exception exception){
		if(exception instanceof EmailFailedException)
			registerFragment.emailFailed(exception.getMessage());
		else if(exception instanceof PasswordFailedException)
			registerFragment.passwordFailed(exception.getMessage());
	}



	@Override
	public void enableManagerInteraction() {

		enableFragment();
		registerSwitchButton.setEnabled(true);
		findViewById(R.id.accountLoadingLayout).setVisibility(View.INVISIBLE);
	}


	@Override
	public void disableManagerInteraction() {
		disableFragment();
		registerSwitchButton.setEnabled(false);
		findViewById(R.id.accountLoadingLayout).setVisibility(View.VISIBLE);
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
	 * Enables the currently active fragment
	 */
	private void enableFragment() {
		if (registerFragment.equals(getSupportFragmentManager().findFragmentByTag("Register")))
			registerFragment.setFragmentInteraction(true);
		else {
			loginFragment.setFragmentInteraction(true);
		}
		registerSwitchButton.setEnabled(true);
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
		registerSwitchButton.setEnabled(false);

	}


}
