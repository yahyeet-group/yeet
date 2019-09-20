package com.yahyeet.boardbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginRegisterActivity extends AppCompatActivity {


    Boolean registerPageBL = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

    }

    /**
     * Method reads info in email and password fields
     * @return a String[] in the form of {email, password}.
     */
    public String[] FetchLoginFields(){
        EditText emailInput = findViewById(R.id.emailInput);
        EditText passInput = findViewById(R.id.passInput);

        // TODO Send information in a better way :S
        return  new String[]{emailInput.getText().toString(), passInput.getText().toString()};

    }

    /**
     * Method (will) logs in with the info in email/password inputs if such a account exists.
     * Alternativly, the method makes a new account if the "Make a New Account" button has been tapped
     * @param view is the visual object (ex a button) the method is bound to (I think)
     */
    public void LoginOrCreateAccount(View view){

        String[] temp = FetchLoginFields();
        if(registerPageBL){
            // TODO Make new account from inputs
            Log.v("test1", "Register account");
        }
        else {
            // TODO Log in as user from temp, save this account somewhere in the model as the current logged in user
            Log.v("test2", "Not Register account");
            ChangeToHome();
        }
    }

    /**
     * Method starts a new home activity.
     */
    void ChangeToHome(){
        // TODO: Change so that old activities are retained and not created anew
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    /**
     * Changes the page details between making a new account or logging in.
     * @param view is the visual object (ex a button) the method is bound to (I think)
     */
    public void ShowRegisterPage(View view){
        Button loginButton = findViewById(R.id.loginButton);
        Button switchButton = findViewById(R.id.newAccountButton);

        // TODO write method in a cleaner form

        if(!registerPageBL){
            loginButton.setText("Register Account");
            switchButton.setText("Return");
        }
        else{

            loginButton.setText("Log In");
            switchButton.setText("Make a New Account");
        }

        registerPageBL = !registerPageBL;
    }

}
