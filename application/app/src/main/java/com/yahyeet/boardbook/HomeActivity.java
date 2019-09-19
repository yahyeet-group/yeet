package com.yahyeet.boardbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void switchToLogin(View view){

        Intent intent = new Intent(this, LoginRegisterActivity.class);
        startActivity(intent);

    }



}
