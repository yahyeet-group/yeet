package com.yahyeet.boardbook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.firebase.FirebaseUserRepository;
import com.yahyeet.boardbook.model.repository.RepositoryResultListener;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        FirebaseUserRepository userRepository = new FirebaseUserRepository(firestore);

        User user = new User();
        user.setEmail("rosen@gren.se");
        user.setName("Rosen");
        userRepository.create(user, new RepositoryResultListener<User>() {
            @Override
            public void onError(Exception e) {
                Log.e("HOME", e.getMessage());
            }

            @Override
            public void onSuccess(User user) {
                Log.d("HOME", user.getEmail() == null ? "This user has no email" : user.getEmail());
            }
        });

        userRepository.all(new RepositoryResultListener<List<User>>() {
            @Override
            public void onError(Exception e) {
                Log.e("HOME", e.getMessage());
            }

            @Override
            public void onSuccess(List<User> users) {
                for (User user : users) {
                    Log.d("HOME", user.getName() == null ? "This user has no name" : user.getName());
                }
            }
        });

        setContentView(R.layout.activity_home);
    }
}
