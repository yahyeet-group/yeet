package com.yahyeet.boardbook.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.model.entity.User;

import java.util.concurrent.CompletableFuture;

public class LoginActivity extends AppCompatActivity {

    EditText emailInput;
    EditText passInput;
    TextView errorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailInput = findViewById(R.id.emailLoginInput);
        passInput = findViewById(R.id.passLoginInput);
        errorText = findViewById(R.id.errorView);

        emailInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

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

    /**
     * Method reads info in email and password fields
     * @return a String[] in the form of {email, password}.
     */
    public String[] FetchLoginFields(){
        // TODO Send information in a better way :S
        return  new String[]{emailInput.getText().toString(), passInput.getText().toString()};

    }

    /**
     * Method (will) logs in with the info in email/password inputs if such a account exists.
     * @param view is the visual object (ex a button) the method is bound to (I think)
     */
    public void LoginAccount(View view){


        // Remove focus from activity
        emailInput.clearFocus();
        passInput.clearFocus();

        String[] temp = FetchLoginFields();

        if(temp[1].length() < 6){
            showErrorMessage();
        }

        BoardbookSingleton.getInstance().getAuthHandler().login(temp[0], temp[1]).thenAccept(u -> {
            // access logged in user from "u"

            Looper.prepare();

            Context context = getApplicationContext();
            CharSequence text = "Hello toast says" + u.getName();
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

        }).exceptionally(e -> {
            // Handle error ("e")

            showErrorMessage();
            e.printStackTrace();

            return null;
        });

        /*
        CompletableFuture<User> userPromise = BoardbookSingleton.getInstance().getAuthHandler().login(temp[0], temp[1]);
        userPromise.thenApply(u -> {
            try {
                BoardbookSingleton.getInstance().getAuthHandler().setLoggedInUser(userPromise.get());
                //finish();
            } catch (Exception e) {
                showErrorMessage();
                e.printStackTrace();
            }
            return null;
        });
        */



    }

    /**
     * Reveals error message on wrong email/password combination
     */
    void showErrorMessage(){
        errorText.setAlpha(1);
    }

    void hideErrorMessage(){
        errorText.setAlpha(0);
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
