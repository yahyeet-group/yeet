package com.yahyeet.boardbook.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.service.IAuthService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RegisterActivity extends AppCompatActivity {


    EditText emailInput;
    EditText passInput;
    EditText userInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailInput = findViewById(R.id.emailRegisterInput);
        passInput = findViewById(R.id.passRegisterInput);
        userInput = findViewById(R.id.usernameRegisterInput);

    }


    /**
     * Method reads info in email and password fields
     * @return a String[] in the form of {email, password}.
     */
    public String[] FetchRegisterFields(){


        // TODO Send information in a better way :S
        return  new String[]{emailInput.getText().toString(), passInput.getText().toString(), userInput.getText().toString()};

    }

    /**
     *  Method makes a new account if the "Make a New Account" button has been tapped
     * @param view is the visual object (ex a button) the method is bound to
     */
    public void RegisterAccount(View view){

        String[] temp = FetchRegisterFields();

        BoardbookSingleton.getInstance().getAuthHandler().signup(temp[0], temp[1], temp[2]).thenAccept(u -> {
            // access logged in user from "u"

            Looper.prepare();

            Context context = getApplicationContext();
            CharSequence text = "Hello toast!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

        }).exceptionally(e -> {
            // Handle error ("e")

            e.printStackTrace();

            return null;
        });


    }
}
