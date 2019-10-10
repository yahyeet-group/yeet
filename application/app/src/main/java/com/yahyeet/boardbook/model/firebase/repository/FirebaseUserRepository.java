package com.yahyeet.boardbook.model.firebase.repository;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.yahyeet.boardbook.model.entity.AbstractEntity;
import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.repository.IUserRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;


public class FirebaseUserRepository implements IUserRepository {
    private FirebaseFirestore firestore;

    private static final String COLLECTION_NAME = "users";

    public FirebaseUserRepository(FirebaseFirestore firestore) {
        this.firestore = firestore;
    }

    @Override
    public CompletableFuture<User> create(User user) {
        if (user.getId() == null) {
            return createFirebaseUserWithoutId(FirebaseUser.fromUser(user)).thenApplyAsync(FirebaseUser::toUser);
        } else {
            return createFirebaseUserWithId(FirebaseUser.fromUser(user)).thenApplyAsync(FirebaseUser::toUser);
        }
    }

    @Override
    public CompletableFuture<User> find(String id) {
        return findFirebaseUserById(id).thenApplyAsync(FirebaseUser::toUser);
    }

    @Override
    public CompletableFuture<User> update(User user) {
        return updateFirebaseUser(FirebaseUser.fromUser(user)).thenApplyAsync(FirebaseUser::toUser);
    }

    @Override
    public CompletableFuture<Void> remove(User user) {
        return removeFirebaseUserById(user.getId());

    }

    @Override
    public CompletableFuture<List<User>> all() {
        return findAllFirebaseUsers().thenApplyAsync(firebaseUsers ->
                firebaseUsers
                        .stream()
                        .map(FirebaseUser::toUser)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public CompletableFuture<List<User>> findFriendsByUserId(String id) {
        return findFirebaseFriendsByUserId(id).thenApplyAsync(firebaseFriends ->
                firebaseFriends
                        .stream()
                        .map(FirebaseUser::toUser)
                        .collect(Collectors.toList())
        );
    }

    private CompletableFuture<List<FirebaseUser>> findFirebaseFriendsByUserId(String id) {
        return findFirebaseUserById(id).thenComposeAsync(firebaseUser -> {
            List<CompletableFuture<FirebaseUser>> completableFutures = firebaseUser.getFriends().stream().map(this::findFirebaseUserById).collect(Collectors.toList());

            CompletableFuture<Void> allFutures = CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0]));
            return allFutures.thenApply(future -> completableFutures.stream().map(CompletableFuture::join).collect(Collectors.toList()));
        });
    }

    private CompletableFuture<List<FirebaseUser>> findAllFirebaseUsers() {
        return CompletableFuture.supplyAsync(() -> {
            Task<QuerySnapshot> task = firestore.collection(COLLECTION_NAME).get();

            try {
                QuerySnapshot querySnapshot = Tasks.await(task);

                return querySnapshot
                        .getDocuments()
                        .stream()
                        .map(documentSnapshot -> documentSnapshot.toObject(FirebaseUser.class))
                        .collect(Collectors.toList());
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        });
    }

    private CompletableFuture<Void> removeFirebaseUserById(String id) {
        return CompletableFuture.supplyAsync(() -> {
            Task<Void> task = firestore.collection(COLLECTION_NAME).document(id).delete();

            try {
                Tasks.await(task);

                return null;
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        });
    }

    private CompletableFuture<FirebaseUser> findFirebaseUserById(String id) {
        return CompletableFuture.supplyAsync(() -> {
            Task<DocumentSnapshot> task = firestore.collection(COLLECTION_NAME).document(id).get();

            try {
                DocumentSnapshot document = Tasks.await(task);

                if (document.exists()) {
                    return document.toObject(FirebaseUser.class);
                }

                throw new CompletionException(new Exception("User not found"));
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        });
    }

    private CompletableFuture<FirebaseUser> createFirebaseUserWithId(FirebaseUser firebaseUser) {
        return CompletableFuture.supplyAsync(() -> {
            Task<Void> task = firestore
                    .collection(COLLECTION_NAME)
                    .document(firebaseUser.getId())
                    .set(firebaseUser);

            try {
                Tasks.await(task);

                return firebaseUser.getId();
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        }).thenCompose(this::findFirebaseUserById);
    }

    private CompletableFuture<FirebaseUser> createFirebaseUserWithoutId(FirebaseUser firebaseUser) {
        return CompletableFuture.supplyAsync(() -> {
            Task<DocumentReference> task = firestore.collection(COLLECTION_NAME)
                    .add(firebaseUser);

            try {
                DocumentReference documentReference = Tasks.await(task);

                return documentReference.getId();
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        }).thenCompose(this::findFirebaseUserById);
    }

    private CompletableFuture<FirebaseUser> updateFirebaseUser(FirebaseUser firebaseUser) {
        return CompletableFuture.supplyAsync(() -> {
            Task<Void> task = firestore.collection(COLLECTION_NAME)
                    .document(firebaseUser.getId())
                    .update(firebaseUser.toMap());

            try {
                Tasks.await(task);

                return firebaseUser.getId();
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        }).thenCompose(this::findFirebaseUserById);
    }

    private CompletableFuture<Void> updateFriendsList(FirebaseUser firebaseUser) {
        return findFirebaseUserById(firebaseUser.getId())
                .thenComposeAsync(user -> {
                    List<String> addedFriends = extractAddedFriends(user.getFriends(), firebaseUser.getFriends());
                    List<String> removedFriends = extractRemovedFriends(user.getFriends(), firebaseUser.getFriends());

                    List<CompletableFuture<Void>> completableFutures = addedFriends
                            .stream()
                            .map(addFriendId -> addFriendToFriendsList(user, addFriendId))
                            .collect(Collectors.toList());
                    completableFutures.addAll(
                            removedFriends
                                    .stream()
                                    .map(removeFriendId -> removeFriendFromFriendsList(user, removeFriendId))
                                    .collect(Collectors.toList())
                    );
                    return CompletableFuture.allOf(
                            completableFutures.toArray(new CompletableFuture[0])
                    );
                });
    }

    private List<String> extractAddedFriends(List<String> localFriends, List<String> remoteFriends) {
        return localFriends
                .stream()
                .filter(localFriendId ->
                        remoteFriends
                                .stream()
                                .noneMatch(remoteFriendId ->
                                        remoteFriendId
                                                .equals(localFriendId)))
                .collect(Collectors.toList());
    }

    private List<String> extractRemovedFriends(List<String> localFriends, List<String> remoteFriends) {
        return remoteFriends
                .stream()
                .filter(remoteFriendId ->
                        localFriends
                                .stream()
                                .noneMatch(localFriendId ->
                                        localFriendId
                                                .equals(remoteFriendId)))
                .collect(Collectors.toList());
    }

    private CompletableFuture<Void> addFriendToFriendsList(FirebaseUser targetUser, String
            friendIdToBeAdded) {
        return findFirebaseUserById(targetUser.getId()).thenComposeAsync(firebaseUser -> {
            if (firebaseUser.getFriends() != null && firebaseUser.getFriends().stream().noneMatch(friendId -> friendId.equals(friendIdToBeAdded))) {
                firebaseUser.getFriends().add(friendIdToBeAdded);
                updateFirebaseUser(firebaseUser);
            }

            return null;
        });
    }

    private CompletableFuture<Void> removeFriendFromFriendsList(FirebaseUser targetUser, String
            friendIdToBeRemoved) {
        return findFirebaseUserById(targetUser.getId()).thenComposeAsync(firebaseUser -> {
            if (firebaseUser.getFriends() != null && firebaseUser.getFriends().stream().anyMatch(friendId -> friendId.equals(friendIdToBeRemoved))) {
                firebaseUser.setFriends(firebaseUser.getFriends().stream().filter(friendId -> !friendId.equals(friendIdToBeRemoved)).collect(Collectors.toList()));
                updateFirebaseUser(firebaseUser);
            }

            return null;
        });
    }
}
