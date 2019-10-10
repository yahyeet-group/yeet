package com.yahyeet.boardbook.activity.FriendsActivity;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.yahyeet.boardbook.R;

public class AddFriendActivity extends AppCompatActivity {

    ImageButton backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfriend);


        backbtn = findViewById(R.id.backButton);

        backbtn.setOnClickListener(view1 -> {
            onBackPressed();
        });


    }
}
