package com.yahyeet.boardbook.activity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.yahyeet.boardbook.R;

public class HomeActivity extends AppCompatActivity {
    public final String yeet = "Yah-Yeet";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
}