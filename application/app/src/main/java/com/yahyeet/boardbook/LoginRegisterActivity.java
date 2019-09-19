package com.yahyeet.boardbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.yahyeet.boardbook.model.entity.User;

public class LoginRegisterActivity extends AppCompatActivity {


    Boolean registerPageBL = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

    }

    public String[] FetchLoginFields(){
        EditText emailInput = findViewById(R.id.emailInput);
        EditText passInput = findViewById(R.id.passInput);

        // TODO Send information in a better way :S
        return  new String[]{emailInput.getText().toString(), passInput.getText().toString()};

    }

    public void LoginOrCreateAccount(View view){

        String[] temp = FetchLoginFields();
        if(registerPageBL){
            // TODO Make new account from fields
            Log.v("test1", "Register account");
        }
        else {
            // TODO Log in as user from temp, send to HomeActivity?
            Log.v("test2", "Not Register account");
            GoToHome();
        }
    }

    void GoToHome(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

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
