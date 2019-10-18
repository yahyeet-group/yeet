package com.yahyeet.boardbook.activity.account;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public abstract class AccountFragment extends Fragment {

	IAccountFragmentHolder accountManager;
	View fragmentContainer;

	EditText etEmail;
	EditText etPassword;

	/**
	 * Saves reference of class inflating the fragment, casts to IAccountFragmentHolder
	 *
	 * @param context is the activity inflating the fragment
	 */
	@Override
	public void onAttach(@NonNull Context context) {
		super.onAttach(context);
		try {
			accountManager = (IAccountFragmentHolder) context;
		} catch (Exception e) {
			// TODO: Implement consequences if fragment fragmentContainer activity does not implement IAccountManagerActivity
			e.printStackTrace();
		}
	}

	/**
	 * Method displays error on login edittext
	 * @param errorText error description
	 */
	void emailFailed(String errorText){
		etEmail.setError(errorText);
	}

	/**
	 * Method displays error on password edittext
	 * @param errorText error description
	 */
	void passwordFailed(String errorText){
		etPassword.setError(errorText);
	}
}