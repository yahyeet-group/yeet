package com.yahyeet.boardbook.activity.accountActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.yahyeet.boardbook.R;

public class LoginFragment extends Fragment {

	private IAccountFragmentHolder accountManager;
	private View fragmentContainer;
	private Button btnLogin;

	private EditText etEmail;
	private EditText etPassword;
	private TextView tvEmail;
	private TextView tvPassword;


	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup view, @Nullable Bundle savedInstanceState) {
		fragmentContainer = view;
		return inflater.inflate(R.layout.fragment_login, view, false);
	}

	/**
	 * Binds buttons, Edittext and TextViews from id to references in class
	 */
	@Override
	public void onStart() {
		super.onStart();

		btnLogin = fragmentContainer.findViewById(R.id.loginButton);
		btnLogin.setOnClickListener(view1 -> loginAccount());

		etEmail = fragmentContainer.findViewById(R.id.loginEmailInput);
		etPassword = fragmentContainer.findViewById(R.id.loginPasswordInput);

		tvEmail = fragmentContainer.findViewById(R.id.loginEmailPrompt);
		tvPassword = fragmentContainer.findViewById(R.id.loginPasswordPrompt);
	}

	//TODO: Abstract to superclass, same functionality in login and register

	/**
	 * Saves reference of class inflating the fragment, casts to IAccount manager
	 *
	 * @param context is the activity inflating the fragment
	 */
	@Override
	public void onAttach(@NonNull Context context) {
		super.onAttach(context);
		accountManager = (IAccountFragmentHolder) context;
	}

	/**
	 * Attempts to call loginAccount from parent activity
	 */
	private void loginAccount() {
		accountManager.loginAccount(etEmail.getText().toString(), etPassword.getText().toString());


	}


	/**
	 * Enables or disables all interactive elements of the fragment
	 *
	 * @param value to enable or disable elements
	 */
	void setFragmentInteraction(Boolean value) {
		etEmail.setEnabled(value);
		etPassword.setEnabled(value);
		btnLogin.setEnabled(value);

		tvEmail.setEnabled(value);
		tvPassword.setEnabled(value);

		etPassword.setText("");
	}

}
