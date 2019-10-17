package com.yahyeet.boardbook.activity.accountActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yahyeet.boardbook.R;

public class RegisterFragment extends AccountFragment {





	private EditText etUser;

	private TextView tvEmail;
	private TextView tvPassword;
	private TextView tvUsername;

	private Button registerButton;


	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup view, @Nullable Bundle savedInstanceState) {
		fragmentContainer = view;
		return inflater.inflate(R.layout.fragment_register, view, false);
	}

	/**
	 * Binds buttons, Edittext and TextViews from id to references in class
	 */
	@Override
	public void onStart() {
		super.onStart();
		etEmail = fragmentContainer.findViewById(R.id.registerEmailInput);
		etPassword = fragmentContainer.findViewById(R.id.registerPasswordInput);
		etUser = fragmentContainer.findViewById(R.id.registerUsernameInput);
		registerButton = fragmentContainer.findViewById(R.id.registerButton);

		tvUsername = fragmentContainer.findViewById(R.id.registerUsernameText);
		tvEmail = fragmentContainer.findViewById(R.id.registerEmailText);
		tvPassword = fragmentContainer.findViewById(R.id.registerPasswordText);


		registerButton.setOnClickListener(view1 -> registerAccount());



		etEmail.addTextChangedListener(new EmailWatcher(etEmail, getResources()));
		etPassword.addTextChangedListener(new PasswordWatcher(etPassword, getResources()));
	}

	/**
	 * Attempts to call registerAccount from fragmentContainer activity
	 */
	private void registerAccount() {
		accountManager.registerAccount(etEmail.getText().toString(), etPassword.getText().toString(), etUser.getText().toString());
	}

	/**
	 * Enables or disables all interactive elements of the fragment
	 *
	 * @param value enable or disable value
	 */
	void setFragmentInteraction(Boolean value) {
		etEmail.setEnabled(value);
		etPassword.setEnabled(value);
		etUser.setEnabled(value);
		registerButton.setEnabled(value);

		tvUsername.setEnabled(value);
		tvEmail.setEnabled(value);
		tvPassword.setEnabled(value);

		etPassword.setText("");

	}

	static class EmailWatcher implements TextWatcher {


		EditText emailText;
		Resources res;

		EmailWatcher(EditText emailText, Resources res) {
			this.emailText = emailText;
			this.res = res;
		}

		@Override
		public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

		}

		@Override
		public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
			if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailText.getText()).matches()) {
				emailText.setError(res.getString(R.string.emailErrorText));
			}
		}

		@Override
		public void afterTextChanged(Editable editable) {


		}
	}

	static class PasswordWatcher implements TextWatcher {


		EditText passwordText;
		Resources res;

		PasswordWatcher(EditText emailText, Resources res) {
			this.passwordText = emailText;
			this.res = res;
		}

		@Override
		public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

		}

		@Override
		public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
			if (passwordText.getText().toString().length() <= 6 &&
				!passwordText.getText().toString().isEmpty()) {

				passwordText.setError(res.getString(R.string.passwordErrorText));
			}
		}

		@Override
		public void afterTextChanged(Editable editable) {


		}
	}


}


