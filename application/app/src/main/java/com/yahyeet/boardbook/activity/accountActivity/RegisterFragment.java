package com.yahyeet.boardbook.activity.accountActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yahyeet.boardbook.R;

public class RegisterFragment extends Fragment {


	private IAccountManager accountManager;
	private View parent;

	private EditText emailInput;
	private EditText passwordInput;
	private EditText usernameInput;

	private TextView emailText;
	private TextView passwordText;
	private TextView usernameText;

	private Button registerButton;

	private boolean emailVaild = false;
	private boolean passwordValid = false;


	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup view, @Nullable Bundle savedInstanceState) {
		parent = view;
		return inflater.inflate(R.layout.fragment_register, view, false);
	}

	/**
	 *	Binds buttons, Edittext and TextViews from id to references in class
	 */
	@Override
	public void onStart() {
		super.onStart();
		emailInput = parent.findViewById(R.id.registerEmailInput);
		passwordInput = parent.findViewById(R.id.registerPasswordInput);
		usernameInput = parent.findViewById(R.id.registerUsernameInput);
		registerButton = parent.findViewById(R.id.registerButton);

		usernameText = parent.findViewById(R.id.registerUsernameText);
		emailText = parent.findViewById(R.id.registerEmailText);
		passwordText = parent.findViewById(R.id.registerPasswordText);


		registerButton.setOnClickListener(view1 -> registerAccount());


		emailInput.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}


			@Override
			public void afterTextChanged(Editable editable) {

				if (!isEmailValid(emailInput.getText())) {
					parent.findViewById(R.id.emailErrorView).setAlpha(1);
					emailVaild = false;
				} else {
					parent.findViewById(R.id.emailErrorView).setAlpha(0);
					emailVaild = true;

				}

				isRegisterButtonValid();

			}
		});
		passwordInput.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void afterTextChanged(Editable editable) {

				if (!isPasswordValid(passwordInput.getText())) {
					parent.findViewById(R.id.registerPasswordError).setAlpha(1);
					passwordValid = false;
				} else {
					parent.findViewById(R.id.registerPasswordError).setAlpha(0);
					passwordValid = true;
				}

				isRegisterButtonValid();
			}
		});
	}

	//TODO: Abstract to superclass, same functionality in login and register
	/**
	 * Saves reference of class inflating the fragment, casts to IAccount manager
	 * @param context is the activity inflating the fragment
	 */
	@Override
	public void onAttach(@NonNull Context context) {
		super.onAttach(context);
		try {
			accountManager = (IAccountManager) context;
		} catch (Exception e) {
			// TODO: Implement consequences if fragment parent activity does not implement IAccountManager
			e.printStackTrace();
		}
	}


	/**
	 * Attempts to call registerAccount from parent activity
	 */
	private void registerAccount() {
		accountManager.registerAccount(emailInput.getText().toString(), passwordInput.getText().toString(), usernameInput.getText().toString());
	}

	/**
	 * 	Enables registerButton if email and passwords are correct, otherwise disables it
	 */
	private void isRegisterButtonValid() {
		if (emailVaild && passwordValid) {
			registerButton.setAlpha(1);
			registerButton.setEnabled(true);

		} else {
			registerButton.setEnabled(false);
			registerButton.setAlpha(0.5f);
		}
	}

	/**
	 * Checks if email matches android pattern for valid email
	 * @param email gets compared against android email pattern
	 * @return if email is valid or not
	 */
	private boolean isEmailValid(CharSequence email) {
		return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
	}

	/**
	 * Checks if password is valid or not
	 * @param password to be validated
	 * @return if password is valid or not
	 */
	private boolean isPasswordValid(CharSequence password) {
		return password.length() >= 6;
	}


	/**
	 * Enables or disables all interactive elements of the fragment
	 *
	 * @param value enable or disable value
	 */
	void setFragmentInteraction(Boolean value) {
		emailInput.setEnabled(value);
		passwordInput.setEnabled(value);
		usernameInput.setEnabled(value);
		registerButton.setEnabled(value);

		usernameText.setEnabled(value);
		emailText.setEnabled(value);
		passwordText.setEnabled(value);
	}

}
