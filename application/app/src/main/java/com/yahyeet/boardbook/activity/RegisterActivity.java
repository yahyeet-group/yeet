package com.yahyeet.boardbook.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.yahyeet.boardbook.R;

public class RegisterActivity extends AppCompatActivity {


    EditText emailInput;
    EditText passwordInput;
    EditText userInput;

    Button registerButton;

    boolean emailVaild;
    boolean passwordValid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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


    /*
    /**
     * Method reads info in email and password fields
     * @return a String[] in the form of {email, password}.

    public String[] FetchRegisterFields(){


        // TODO Send information in a better way :S
        return  new String[]{emailInput.getText().toString(), passwordInput.getText().toString(), userInput.getText().toString()};

    }
    */

    /**
     *  Method makes a new account if the "Make a New Account" button has been tapped
     * @param view is the visual object (ex a button) the method is bound to
     */
    public void RegisterAccount(View view){


        ProgressDialog progress = new ProgressDialog(this);
        // TODO Make better loading text
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();


        BoardbookSingleton.getInstance().getAuthHandler().signup(
                emailInput.getText().toString(), passwordInput.getText().toString(), userInput.getText().toString()).thenAccept(u -> {
            // access logged in user from "u"

            progress.dismiss();
            finish();

        }).exceptionally(e -> {
            // Handle error ("e")

            progress.dismiss();
            e.printStackTrace();

            return null;
        });

    }


    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    boolean isPasswordValid(CharSequence password){
        return password.length() >= 6;
    }

}
