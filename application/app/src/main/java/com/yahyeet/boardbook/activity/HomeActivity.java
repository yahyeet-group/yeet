package com.yahyeet.boardbook.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yahyeet.boardbook.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        changeTest();
    }

    /**
     * This method when called starts a new login activity
     * @param view is the visual object (ex a button) the method is bound to (I think)
     */
    public void ChangeToLogin(View view){
        // TODO: Change so that old activities are retained and not created anew
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }

    void changeTest(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }



}
