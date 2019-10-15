package com.yahyeet.boardbook.model.firebase.service;

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

public class FirebaseAuthService implements IAuthService {

    private static final String TAG = "Authentication";

    private FirebaseAuth firebaseAuth;
    private IUserRepository userRepository;

    public FirebaseAuthService(FirebaseAuth firebaseAuth, IUserRepository userRepository){
        this.firebaseAuth = firebaseAuth;
        this.userRepository = userRepository;
    }

    @Override
    public CompletableFuture<User> login(String email, String password) {
        return CompletableFuture.supplyAsync(() -> {
            Task<AuthResult> task = firebaseAuth.signInWithEmailAndPassword(email, password);

            try {
                AuthResult result = Tasks.await(task);
                Log.d(TAG, "signInWithEmail:success");
                FirebaseUser firebaseUser = result.getUser();
                assert firebaseUser != null;

                return firebaseUser.getUid();
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        }).thenCompose(uid -> userRepository.find(uid));
    }

    @Override
    public CompletableFuture<Void> logout(){
        firebaseAuth.signOut();
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<User> signup(String email, String password, String name) {
        return CompletableFuture.supplyAsync(() -> {
            Task<AuthResult> task = firebaseAuth.createUserWithEmailAndPassword(email, password);

            try {
                AuthResult result = Tasks.await(task);
                Log.d(TAG, "createUserWithEmail:success");
                FirebaseUser firebaseUser = result.getUser();
                assert firebaseUser != null;
                User user = new User(name);
                user.setId(firebaseUser.getUid());
                return user;
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        });
    }
}