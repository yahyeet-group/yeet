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
    private View parent;

    private EditText emailInput;
    private EditText passInput;
    private TextView errorText;

    private Button loginButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup view, @Nullable Bundle savedInstanceState) {
        parent = view;
        return inflater.inflate(R.layout.fragment_login, view, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        loginButton = parent.findViewById(R.id.loginButton);

        emailInput = parent.findViewById(R.id.emailLoginInput);
        passInput = parent.findViewById(R.id.passLoginInput);
        errorText = parent.findViewById(R.id.errorView);

        loginButton.setOnClickListener(view1 -> loginAccount());
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
        passInput.addTextChangedListener(new TextWatcher() {
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
        passInput.clearFocus();

        if (passInput.length() < 6) {
            showErrorMessage();
            return;
        }

        accountManager.loginAccount(emailInput.getText().toString(), passInput.getText().toString());


    }





    /**
     * Reveals error message on wrong email/password combination
     */
    public void showErrorMessage() {
        errorText.setAlpha(1);
    }

    public void hideErrorMessage() {
        errorText.setAlpha(0);
    }


}
