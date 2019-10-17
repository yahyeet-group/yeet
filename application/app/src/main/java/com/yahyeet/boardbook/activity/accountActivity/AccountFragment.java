package com.yahyeet.boardbook.activity.accountActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.yahyeet.boardbook.R;

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


	void emailFailed(String errorText){
		etEmail.setError(errorText);
	}

	void passwordFailed(String errorText){
		etPassword.setError(errorText);
	}
}
