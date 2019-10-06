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

import com.yahyeet.boardbook.R;

public class RegisterFragment extends Fragment{


    private IAccountManager accountManager;
    private View parent;

    private EditText emailInput;
    private EditText passwordInput;
    private EditText userInput;

    private Button registerButton;

    private boolean emailVaild = false;
    private boolean passwordValid = false;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parent = view;


        return inflater.inflate(R.layout.fragment_register, view, false);
    }


    @Override
    public void onStart() {
        super.onStart();
        emailInput = parent.findViewById(R.id.emailRegisterInput);
        passwordInput = parent.findViewById(R.id.passwordRegisterInput);
        userInput = parent.findViewById(R.id.usernameRegisterInput);
        registerButton = parent.findViewById(R.id.registerButton);

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

                checkRegisterButtonValid();

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
                    parent.findViewById(R.id.passwordErrorView).setAlpha(1);
                    passwordValid = false;
                } else {
                    parent.findViewById(R.id.passwordErrorView).setAlpha(0);
                    passwordValid = true;
                }

                checkRegisterButtonValid();
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
            // TODO: Implement consequences if fragment parent activity does not implement IAccountManager
            e.printStackTrace();
        }
    }

    private void checkRegisterButtonValid() {
        if (emailVaild && passwordValid) {
            registerButton.setAlpha(1);
            registerButton.setClickable(true);

        } else {
            registerButton.setClickable(false);
            registerButton.setAlpha(0.5f);
        }
    }


    /**
     * Method makes a new account if the "Make a New Account" button has been tapped
     */
    private void registerAccount() {
        accountManager.registerAccount(emailInput.getText().toString(), passwordInput.getText().toString(), userInput.getText().toString());
    }


    private boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordValid(CharSequence password) {
        return password.length() >= 6;
    }


}
