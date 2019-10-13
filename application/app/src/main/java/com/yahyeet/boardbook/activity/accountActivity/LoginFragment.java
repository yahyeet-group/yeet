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

    /**
     * Binds buttons, Edittext and TextViews from id to references in class
     */
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

    /**
     * Saves reference of class inflating the fragment, casts to IAccount manager
     * @param context is the activity inflating the fragment
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        accountManager = (IAccountManager) context;
    }

    /**
     * Attempts to call loginAccount from parent activity
     */
    private void loginAccount() {
        accountManager.loginAccount(emailInput.getText().toString(), passwordInput.getText().toString());


    }

    /**
     * Reveals error message
     */
    void showErrorMessage() {
        errorText.setAlpha(1);
    }

    /**
     * Hides error message
     */
    void hideErrorMessage() {
        errorText.setAlpha(0);
    }

    /**
     * Enables or disables all interactive elements of the fragment
     * @param value to enable or disable elements
     */
    void setFragmentInteraction(Boolean value){
        emailInput.setEnabled(value);
        passwordInput.setEnabled(value);
        loginButton.setEnabled(value);

        emailText.setEnabled(value);
        passwordText.setEnabled(value);
    }

}
