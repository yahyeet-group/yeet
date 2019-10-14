package com.yahyeet.boardbook.activity.accountActivity;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.yahyeet.boardbook.R;

public class LoginFragment extends Fragment{

    private IAccountManager accountManager;
    private View fragmentContainer;
    private Button loginButton;

    private EditText emailInput;
    private EditText passwordInput;
    private TextView errorText;
    private TextView emailText;
    private TextView passwordText;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup view, @Nullable Bundle savedInstanceState) {
        fragmentContainer = view;
        return inflater.inflate(R.layout.fragment_login, view, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        loginButton = fragmentContainer.findViewById(R.id.loginButton);
        loginButton.setOnClickListener(view1 -> loginAccount());

        emailInput = fragmentContainer.findViewById(R.id.loginEmailInput);
        passwordInput = fragmentContainer.findViewById(R.id.loginPasswordInput);
        errorText = fragmentContainer.findViewById(R.id.loginErrorText);

        emailText = fragmentContainer.findViewById(R.id.loginEmailPrompt);
        passwordText = fragmentContainer.findViewById(R.id.loginPasswordPrompt);

        emailInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                hideErrorMessage();
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
                hideErrorMessage();
            }
        });
    }

    //TODO: Abstract to superclass, same functionality in login and register
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            accountManager = (IAccountManager) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loginAccount() {
        // Remove focus from activity
        emailInput.clearFocus();
        passwordInput.clearFocus();

        if (passwordInput.length() < 6) {
            showErrorMessage();
            return;
        }

        accountManager.loginAccount(emailInput.getText().toString(), passwordInput.getText().toString());


    }

    /**
     * Reveals error message on wrong email/password combination
     */
    private void showErrorMessage() {
        errorText.setAlpha(1);
    }

    private void hideErrorMessage() {
        errorText.setAlpha(0);
    }

    /**
     * Enables or disables all interactive elements of the fragment
     * @param value enable or disable value
     */
    void setFragmentInteraction(Boolean value){
        emailInput.setEnabled(value);
        passwordInput.setEnabled(value);
        loginButton.setEnabled(value);

        emailText.setEnabled(value);
        passwordText.setEnabled(value);
    }

}
