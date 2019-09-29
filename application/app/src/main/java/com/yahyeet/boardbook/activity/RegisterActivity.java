package com.yahyeet.boardbook.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.presenter.RegisterPresenter;

public class RegisterActivity extends AppCompatActivity implements IRegisterActivity{


    RegisterPresenter registerPresenter;

    EditText emailInput;
    EditText passwordInput;
    EditText userInput;

    Button registerButton;

    boolean emailVaild = false;
    boolean passwordValid = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // TODO: Make accountManagerActivity (To be created) assign loginPresenter and registerPresenter
        registerPresenter = new RegisterPresenter(this);


        emailInput = findViewById(R.id.emailRegisterInput);
        passwordInput = findViewById(R.id.passwordRegisterInput);
        userInput = findViewById(R.id.usernameRegisterInput);
        registerButton = findViewById(R.id.registerButton);


        emailInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(!isEmailValid(emailInput.getText())){
                    findViewById(R.id.emailErrorView).setAlpha(1);
                    emailVaild = false;
                }
                else{
                    findViewById(R.id.emailErrorView).setAlpha(0);
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

                if(!isPasswordValid(passwordInput.getText())){
                    findViewById(R.id.passwordErrorView).setAlpha(1);
                    passwordValid = false;
                }
                else{
                    findViewById(R.id.passwordErrorView).setAlpha(0);
                    passwordValid = true;
                }

                checkRegisterButtonValid();
            }
        });
    }



    void checkRegisterButtonValid(){
        if(emailVaild && passwordValid){
            registerButton.setAlpha(1);
            registerButton.setClickable(true);

        }
        else{
            registerButton.setClickable(false);
            registerButton.setAlpha(0.5f);
        }
    }

    public RegisterPresenter getRegisterPresenter() {
        return registerPresenter;
    }

    public void setRegisterPresenter(RegisterPresenter registerPresenter) {
        this.registerPresenter = registerPresenter;
    }

    /**
     *  Method makes a new account if the "Make a New Account" button has been tapped
     * @param view is the visual object (ex a button) the method is bound to
     */
    public void RegisterAccount(View view){
        registerPresenter.signup(emailInput.getText().toString(), passwordInput.getText().toString(), userInput.getText().toString());
    }


    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    boolean isPasswordValid(CharSequence password){
        return password.length() >= 6;
    }


    @Override
    public void finish(){
        super.finish();
    }

    /**
     * Shows a new login activity
     * @param view that method is attached to
     */
    public void showLoginPage(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
