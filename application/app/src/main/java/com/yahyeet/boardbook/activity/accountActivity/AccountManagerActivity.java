package com.yahyeet.boardbook.activity.accountActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.activity.ProfileActivity;
import com.yahyeet.boardbook.presenter.AccountManagerPresenter;

public class AccountManagerActivity extends AppCompatActivity implements IAccountManager {

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

	public void loginAccount(String email, String password) {
		accountManagerPresenter.loginAccount(email, password);
	}

	public void registerAccount(String email, String password, String username) {
		accountManagerPresenter.registerAccount(email, password, username);
	}

	@Override
	public void finishAccountManager() {
		Intent startHome = new Intent(this, ProfileActivity.class);
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

	private void showLoginFragment() {
		registerSwitchButton.setVisibility(View.VISIBLE);
		getSupportFragmentManager().beginTransaction().replace(R.id.accountManagerContainer, loginFragment, "Login").commit();
	}

	private void showRegisterFragment() {
		registerSwitchButton.setVisibility(View.INVISIBLE);
		getSupportFragmentManager().beginTransaction().replace(R.id.accountManagerContainer, registerFragment, "Register").commit();
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

	private void enableFragment() {
		if (registerFragment.equals(getSupportFragmentManager().findFragmentByTag("Register")))
			registerFragment.setFragmentInteraction(true);
		else {
			loginFragment.setFragmentInteraction(true);
		}
	}

	private void disableFragment() {
		if (registerFragment.equals(getSupportFragmentManager().findFragmentByTag("Register")))
			registerFragment.setFragmentInteraction(false);
		else {
			loginFragment.setFragmentInteraction(false);
		}
	}


}
