package com.yahyeet.boardbook.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.service.IAuthService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RegisterActivity extends AppCompatActivity {


    IAuthService auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

    }


    /**
     * Method reads info in email and password fields
     * @return a String[] in the form of {email, password}.
     */
    public String[] FetchRegisterFields(){
        EditText emailInput = findViewById(R.id.emailRegisterInput);
        EditText passInput = findViewById(R.id.passRegisterInput);
        EditText userInput = findViewById(R.id.usernameRegisterInput);

        // TODO Send information in a better way :S
        return  new String[]{emailInput.getText().toString(), passInput.getText().toString(), userInput.getText().toString()};

    }

    /**
     *  Method makes a new account if the "Make a New Account" button has been tapped
     * @param view is the visual object (ex a button) the method is bound to
     */
    public void RegisterAccount(View view){

        String[] temp = FetchRegisterFields();
        CompletableFuture<User> userPromise = BoardbookSingleton.getInstance().getAuthHandler().signup(temp[0], temp[1], temp[2]);
        userPromise.thenApply(u -> {
            try {
                BoardbookSingleton.getInstance().getAuthHandler().setLoggedInUser(userPromise.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });
        finish();

    }
}
