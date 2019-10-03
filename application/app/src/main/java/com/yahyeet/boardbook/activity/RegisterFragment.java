package com.yahyeet.boardbook.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.presenter.LoginPresenter;

public class RegisterFragment extends Fragment implements ILoginActivity{


    LoginPresenter loginPresenter;

    EditText emailInput;
    EditText passwordInput;
    EditText userInput;

    Button registerButton;

    boolean emailVaild = false;
    boolean passwordValid = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.fragment_register);

        // TODO: Make accountManagerActivity (To be created) assign loginPresenter and registerPresenter
        loginPresenter = new LoginPresenter(this);


        emailInput = getView().findViewById(R.id.emailRegisterInput);
        passwordInput = getView().findViewById(R.id.passwordRegisterInput);
        userInput = getView().findViewById(R.id.usernameRegisterInput);
        registerButton = getView().findViewById(R.id.registerButton);
        this.


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
                    getView().findViewById(R.id.emailErrorView).setAlpha(1);
                    emailVaild = false;
                } else {
                    getView().findViewById(R.id.emailErrorView).setAlpha(0);
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
                    getView().findViewById(R.id.passwordErrorView).setAlpha(1);
                    passwordValid = false;
                } else {
                    getView().findViewById(R.id.passwordErrorView).setAlpha(0);
                    passwordValid = true;
                }

                checkRegisterButtonValid();
            }
        });
    }


    void checkRegisterButtonValid() {
        if (emailVaild && passwordValid) {
            registerButton.setAlpha(1);
            registerButton.setClickable(true);

        } else {
            registerButton.setClickable(false);
            registerButton.setAlpha(0.5f);
        }
    }

    /*
    public RegisterPresenter getRegisterPresenter() {
        return registerPresenter;
    }

    public void setRegisterPresenter(RegisterPresenter registerPresenter) {
        this.registerPresenter = registerPresenter;
    }

     */

    /*
     * Method makes a new account if the "Make a New Account" button has been tapped
     *
     * @param view is the visual object (ex a button) the method is bound to
     */
    /*
    public void RegisterAccount(View view) {
        registerPresenter.signup(emailInput.getText().toString(), passwordInput.getText().toString(), userInput.getText().toString());
    }
    */



    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    boolean isPasswordValid(CharSequence password) {
        return password.length() >= 6;
    }

    @Override
    public LoginPresenter getLoginPresenter() {
        return null;
    }

    @Override
    public void setLoginPresenter(LoginPresenter loginPresenter) {

    }

    @Override
    public void loginAccount(View view) {

    }

    @Override
    public void showErrorMessage() {

    }

    @Override
    public void hideErrorMessage() {

    }

    /*
    @Override
    public void finish() {
        super.finish();
    }

    /**
     * Shows a new login activity
     *
     * @param view that method is attached to
     */

    public void showLoginPage(View view) {
        //Intent intent = new Intent(this, LoginFragment.class);
        //startActivity(intent);
        //finish();
    }

}
