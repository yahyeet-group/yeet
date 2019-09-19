package com.yahyeet.boardbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.yahyeet.boardbook.model.entity.User;

public class LoginActivity extends AppCompatActivity {


    Boolean makeNewAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void RegisterNewAccount(View view){
        // TODO To be continued
    }


    public String[] FetchLoginFields(){
        EditText emailInput = findViewById(R.id.emailInput);
        EditText passInput = findViewById(R.id.passInput);

        // TODO Send information in a better way :S
        return  new String[]{emailInput.getText().toString(), passInput.getText().toString()};

    }

    public void LoginOrCreateAccount(View view){

        String[] temp = FetchLoginFields();
        if(makeNewAccount){
            // TODO Make new account from fields
        }
        else {
            User testr = new User("Somenumber", temp[0], temp[1]);
            if(testr.getId().equals("Somenumber")){
                GoToHome();
            }
        }
    }

    void GoToHome(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

}
