package com.yahyeet.boardbook.model.firebase;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.repository.IUserRepository;
import com.yahyeet.boardbook.model.service.IAuthService;

import java.util.concurrent.atomic.AtomicBoolean;

public class FirebaseAuthService implements IAuthService {

    private static final String TAG = "Authentication";

    private FirebaseAuth mAuth;

    public FirebaseAuthService(){
        mAuth = FirebaseAuth.getInstance();
    }

    public User logIn(String email, String password){
       return null;
    }

    public void logOut(){
        mAuth.signOut();
    }

    public void register(String email, String password, String name) throws Exception {
        AtomicBoolean success = new AtomicBoolean(false);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        assert user != null;
                        addUser(user.getUid(), name);
                        success.set(true);
                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        success.set(false);
                    }
                });
        if(!success.get()) throw new Exception();
    }

    private void addUser(String Uid, String name){
        User user = new User(Uid, name);
        //TODO: Make this use a RepositoryContainer or some other solution instead of creating a new reference!
        IUserRepository userRepository = new FirebaseUserRepository();
        userRepository.Add(user);
    }
}