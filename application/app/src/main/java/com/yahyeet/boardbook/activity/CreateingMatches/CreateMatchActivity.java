package com.yahyeet.boardbook.activity.CreateingMatches;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.activity.CreateingMatches.fragments.SelectGameFragment;

public class CreateMatchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_match);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SelectGameFragment()).commit();
    }
}
