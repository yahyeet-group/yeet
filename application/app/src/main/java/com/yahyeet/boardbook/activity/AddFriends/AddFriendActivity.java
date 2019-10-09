package com.yahyeet.boardbook.activity.AddFriends;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.activity.HomeActivity;

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
