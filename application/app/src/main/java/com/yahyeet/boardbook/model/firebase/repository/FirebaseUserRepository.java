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

    public static final String COLLECTION_NAME = "users";

    public FirebaseUserRepository(FirebaseFirestore firestore) {
        this.firestore = firestore;
    }

    @Override
    public CompletableFuture<User> create(User user) {
        if (user.getId() == null) {
            return createWithoutId(user);
        } else {
            return createWithId(user);
        }
    }

    @Override
    public CompletableFuture<User> find(String id) {
        return CompletableFuture.supplyAsync(() -> {
            Task<DocumentSnapshot> task = firestore.collection(COLLECTION_NAME).document(id).get();

            try {
                DocumentSnapshot document = Tasks.await(task);

                if (document.exists()) {
                    return documentToUser(document);
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
            Task<Void> task = firestore.collection(COLLECTION_NAME)
                    .document(user.getId()).update
                    .update(userToMap(user));

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
                    FirebaseUser user = documentSnapshot.toObject(FirebaseUser.class);
                    users.add(documentToUser(documentSnapshot));
                }

                return users;
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        });
    }

    @Override
    public CompletableFuture<List<User>> findFriendsByUserId(String id) {
        return CompletableFuture.supplyAsync(() -> {
            Task<DocumentSnapshot> task = firestore.collection(COLLECTION_NAME).document(id).get();

            try {
                DocumentSnapshot document = Tasks.await(task);

                if (document.exists()) {
                    if (document.contains("friends")) {
                        return (List<String>) document.get("friends");
                    } else {
                        throw new CompletionException(new Exception("User has no friends list"));
                    }
                }

                throw new CompletionException(new Exception("User not found"));
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        }).thenComposeAsync(friendIds -> {
            List<CompletableFuture<User>> completableFutures = friendIds.stream().map(this::find).collect(Collectors.toList());

            CompletableFuture<Void> allFutures = CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0]));
            return allFutures.thenApply(future -> completableFutures.stream().map(CompletableFuture::join).collect(Collectors.toList()));
        });
    }

    private CompletableFuture<List<String>> findFriendIdsByUserId(String id) {
        return findFirebaseUserById(id).thenApplyAsync(user -> user.getFriends());
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

    @Override
    public CompletableFuture<Void> addFriend(User currentUser, User targetUser) {
        return null;
    }

    @Override
    public CompletableFuture<Void> removeFriend(User currentUser, User targetUser) {
        return null;
    }

    private static FirebaseUser userToFirebaseUser(User user) {
        FirebaseUser firebaseUser = new FirebaseUser(user.getId(), user.getName(), null);

        if (user.getFriends() != null) {
            firebaseUser.setFriends(
                    user
                            .getFriends()
                            .stream()
                            .map(AbstractEntity::getId)
                            .collect(Collectors.toList())
            );
        }

        return firebaseUser;
    }

    private static User firebaseUserToUser(FirebaseUser firebaseUser) {
        return new User(firebaseUser.getId(), firebaseUser.getName());
    }

    private static Map<String, Object> userToMap(User user) {
        Map<String, Object> userMap = new HashMap<>();

        userMap.put("name", user.getName());

        return userMap;
    }

    private CompletableFuture<User> createWithId(User user) {
        return CompletableFuture.supplyAsync(() -> {
            Task<Void> task = firestore
                    .collection(COLLECTION_NAME)
                    .document(user.getId())
                    .set(userToMap(user));

            try {
                Tasks.await(task);

                return user.getId();
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        }).thenCompose(this::find);
    }

    private CompletableFuture<User> createWithoutId(User user) {
        return CompletableFuture.supplyAsync(() -> {
            Task<DocumentReference> task = firestore.collection(COLLECTION_NAME)
                    .add(userToMap(user));

            try {
                DocumentReference documentReference = Tasks.await(task);

                return documentReference.getId();
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        }).thenCompose(this::find);
    }

    private CompletableFuture<Void> updateFirebaseUser(FirebaseUser firebaseUser) {
        return CompletableFuture.supplyAsync(() -> {
            Task<Void> task = firestore.collection(COLLECTION_NAME)
                    .document(firebaseUser.getId())
                    .update(firebaseUser.toMap());

            try {
                Tasks.await(task);

                return user.getId();
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        }).thenCompose(this::find);
    }

    private CompletableFuture<Void> addFriendToFriendsList(User targetUser, User friendToBeAdded) {
        return findFirebaseUserById(targetUser.getId()).thenComposeAsync(firebaseUser -> {
            if (firebaseUser.getFriends() != null && firebaseUser.getFriends().stream().noneMatch(friendId -> friendId.equals(friendToBeAdded.getId()))) {
                firebaseUser.getFriends().add(friendToBeAdded.getId());
                updateFirebaseUser(firebaseUser);
            }

            return null;
        });
    }

    private CompletableFuture<Void> removeFriendFromFriendsList(User targetUser, User friendToBeRemoved) {
        return findFirebaseUserById(targetUser.getId()).thenComposeAsync(firebaseUser -> {
            if (firebaseUser.getFriends() != null && firebaseUser.getFriends().stream().anyMatch(friendId -> friendId.equals(friendToBeRemoved.getId()))) {
                firebaseUser.setFriends(firebaseUser.getFriends().stream().filter(friendId -> !friendId.equals(friendToBeRemoved.getId())).collect(Collectors.toList()));
                updateFirebaseUser(firebaseUser);
            }

            return null;
        });
    }
}
