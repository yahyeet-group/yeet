package com.yahyeet.boardbook.model.firebase;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.repository.IUserRepository;
import com.yahyeet.boardbook.model.service.IAuthService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class FirebaseAuthService implements IAuthService {

    private static final String TAG = "Authentication";

    private FirebaseAuth firebaseAuth;

    public FirebaseAuthService(){
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public CompletableFuture<String> login(String email, String password) {
        return CompletableFuture.supplyAsync(() -> {
            Task<AuthResult> task = firebaseAuth.signInWithEmailAndPassword(email, password);

            try {
                AuthResult result = Tasks.await(task);
                Log.d(TAG, "signInWithEmail:success");
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                assert firebaseUser != null;
                return firebaseUser.getUid();
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        });
    }

    @Override
    public void logout(){
        firebaseAuth.signOut();
    }

    @Override
    public CompletableFuture<User> signup(String email, String password, String name) {
        return CompletableFuture.supplyAsync(() -> {
            Task<AuthResult> task = firebaseAuth.createUserWithEmailAndPassword(email, password);

            try {
                AuthResult result = Tasks.await(task);
                Log.d(TAG, "createUserWithEmail:success");
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                assert firebaseUser != null;
                return new User(firebaseUser.getUid(), name);
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        });
    }

    private void addUser(String Uid, String name){
        User user = new User(Uid, name);
        userRepository.Add(user);
    }

    private User getSignedInUser(String uid) {
        User user = userRepository.Find(uid);
        return user;
    }
}