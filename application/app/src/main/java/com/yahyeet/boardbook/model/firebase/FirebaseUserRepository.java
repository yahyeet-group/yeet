package com.yahyeet.boardbook.model.firebase;

import android.app.ExpandableListActivity;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.repository.IUserRepository;
import com.yahyeet.boardbook.model.repository.RepositoryResultListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;


public class FirebaseUserRepository implements IUserRepository {
    private FirebaseFirestore firestore;

    public static final String COLLECTION_NAME = "users";

    public FirebaseUserRepository(FirebaseFirestore firestore) {
        this.firestore = firestore;
    }

    public CompletableFuture<User> createWithPromise(User user) {
        return CompletableFuture.supplyAsync(() -> {
            Map<String, Object> userInput = new HashMap<>();
            userInput.put("email", user.getEmail());
            userInput.put("name", user.getName());

            Task<DocumentReference> task = firestore.collection(COLLECTION_NAME)
                    .add(userInput);

            try {
                DocumentReference documentReference = Tasks.await(task);
                return documentReference.getId();
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        }).thenCompose(this::findWithPromise);
    }

    public CompletableFuture<User> findWithPromise(String id) {
        return CompletableFuture.supplyAsync(() -> {
            Task<DocumentSnapshot> task = firestore.collection(COLLECTION_NAME).document(id).get();

            try {
                DocumentSnapshot document = Tasks.await(task);
                if (document.exists()) {
                    User user = document.toObject(User.class);
                    user.setId(document.getId());
                    return user;
                }

                throw new CompletionException(new Exception("User not found"));
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        });
    }

    @Override
    public void create(User user, RepositoryResultListener<User> listener) {
        Map<String, Object> userInput = new HashMap<>();
        userInput.put("email", user.getEmail());
        userInput.put("name", user.getName());


        firestore.collection(COLLECTION_NAME)
                .add(userInput).addOnSuccessListener(documentReference ->
                this.find(documentReference.getId(), listener)
        ).addOnFailureListener(listener::onError);
    }

    @Override
    public void find(String id, RepositoryResultListener<User> listener) {
        firestore.collection(COLLECTION_NAME).document(id).get().addOnSuccessListener((documentSnapshot) -> {
            if (documentSnapshot.exists()) {
                User user = documentSnapshot.toObject(User.class);
                user.setId(documentSnapshot.getId());
                listener.onSuccess(user);
            } else {
                listener.onError(new Exception("User not found"));
            }
        }).addOnFailureListener(listener::onError);
    }

    @Override
    public void update(User user, RepositoryResultListener<User> listener) {
        Map<String, Object> userInput = new HashMap<>();
        userInput.put("email", user.getEmail());
        userInput.put("name", user.getName());

        firestore.collection(COLLECTION_NAME).document(user.getId()).update(userInput).addOnSuccessListener((aVoid) ->
                this.find(user.getId(), listener)
        ).addOnFailureListener(listener::onError);
    }

    @Override
    public void remove(User user, RepositoryResultListener<Void> listener) {
        firestore.collection(COLLECTION_NAME).document(user.getId()).delete().addOnSuccessListener((aVoid) ->
                listener.onSuccess(null)
        ).addOnFailureListener(listener::onError);
    }

    @Override
    public void all(RepositoryResultListener<List<User>> listener) {
        firestore.collection(COLLECTION_NAME).get().addOnSuccessListener((documentSnapshots) -> {
            List<User> users = new ArrayList<>();
            for (QueryDocumentSnapshot document : documentSnapshots) {
                User user = document.toObject(User.class);
                user.setId(document.getId());
                users.add(user);
            }
            listener.onSuccess(users);
        }).addOnFailureListener(listener::onError);
    }
}
