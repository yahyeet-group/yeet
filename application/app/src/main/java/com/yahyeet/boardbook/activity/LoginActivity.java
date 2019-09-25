package com.yahyeet.boardbook.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.service.IAuthService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {


    IAuthService auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //auth = (IAuthService) getIntent().getSerializableExtra("BB");

    }

    /**
     * Method reads info in email and password fields
     * @return a String[] in the form of {email, password}.
     */
    public String[] FetchLoginFields(){
        EditText emailInput = findViewById(R.id.emailLoginInput);
        EditText passInput = findViewById(R.id.passLoginInput);

        // TODO Send information in a better way :S
        return  new String[]{emailInput.getText().toString(), passInput.getText().toString()};

    }

    /**
     * Method (will) logs in with the info in email/password inputs if such a account exists.
     * @param view is the visual object (ex a button) the method is bound to (I think)
     */
    public void LoginAccount(View view){

        String[] temp = FetchLoginFields();
        CompletableFuture<User> userPromise = BoardbookSingleton.getInstance().getAuthHandler().login(temp[0], temp[1]);
        userPromise.thenApply(u -> {
            try {
                BoardbookSingleton.getInstance().getAuthHandler().setLoggedInUser(userPromise.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });
        //finish();




    }

    /**
     * Starts a new activity of registry page and finishes this one.
     * @param view is the visual object (ex a button) the method is bound to (I think)
     */
    public void ShowRegisterPage(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

}
