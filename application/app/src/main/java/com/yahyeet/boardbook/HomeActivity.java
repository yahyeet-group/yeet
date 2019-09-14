package com.yahyeet.boardbook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    private String myText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button btn1 = findViewById(R.id.btn1);
        

        btn1.setOnClickListener((n) -> {btn1.setText("Yeet");});


    }

    protected void hello (){
        myText = "yeet";
    }
}
