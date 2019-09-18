package com.yahyeet.boardbook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.yahyeet.boardbook.model.firebase.FirebaseAuthService;
import com.yahyeet.boardbook.model.service.IAuthService;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        IAuthService auth = new FirebaseAuthService();
        auth.register("sasdas@gmail.com", "Apa123", "C2arl");
    }
}
