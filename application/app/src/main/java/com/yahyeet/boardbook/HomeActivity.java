package com.yahyeet.boardbook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.firebase.FirebaseUserRepository;

public class HomeActivity extends AppCompatActivity {

    public final String yeet = "Yah-Yeet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        FirebaseUserRepository userRepository = new FirebaseUserRepository(firestore);

        User user = new User();
        user.setEmail("rosen@gren.se");
        user.setName("Rosen");

        userRepository.create(user).thenAccept(u -> {
            Log.d("TEST", "User email: " + u.getEmail() + " user id: " + u.getId());
        }).exceptionally(e -> {
            Log.d("TEST", e.getMessage());
            return null;
        });

        setContentView(R.layout.activity_home);
    }
}
