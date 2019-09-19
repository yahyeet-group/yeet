package com.yahyeet.boardbook.model.firebase;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.repository.IUserRepository;

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

    @Override
    public CompletableFuture<User> create(User user) {
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
        }).thenCompose(this::find);
    }

    @Override
    public CompletableFuture<User> find(String id) {
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
    public CompletableFuture<User> update(User user) {
        return CompletableFuture.supplyAsync(() -> {
            Map<String, Object> userInput = new HashMap<>();
            userInput.put("email", user.getEmail());
            userInput.put("name", user.getName());

            Task<Void> task = firestore.collection(COLLECTION_NAME).document(user.getId()).update(userInput);

            try {
                Tasks.await(task);

                return user.getId();
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        }).thenCompose(this::find);
    }

    @Override
    public CompletableFuture<Void> remove(User user) {
        return CompletableFuture.supplyAsync(() -> {
            Task<Void> task = firestore.collection(COLLECTION_NAME).document(user.getId()).delete();

            try {
                Tasks.await(task);

                return null;
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        });
    }

    @Override
    public CompletableFuture<List<User>> all() {
        return CompletableFuture.supplyAsync(() -> {
            Task<QuerySnapshot> task = firestore.collection(COLLECTION_NAME).get();

            try {
                QuerySnapshot querySnapshot = Tasks.await(task);

                List<User> users = new ArrayList<>();
                for (QueryDocumentSnapshot documentSnapshot : querySnapshot) {
                    User user = documentSnapshot.toObject(User.class);
                    user.setId(documentSnapshot.getId());
                    users.add(user);
                }

                return users;
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        });
    }
}
