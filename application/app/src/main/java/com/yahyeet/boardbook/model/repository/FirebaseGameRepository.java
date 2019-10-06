package com.yahyeet.boardbook.model.repository;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.model.repository.IGameRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class FirebaseGameRepository implements IGameRepository {
    private FirebaseFirestore firestore;

    public static final String COLLECTION_NAME = "games";

    public FirebaseGameRepository(FirebaseFirestore firestore) {
        this.firestore = firestore;
    }

    @Override
    public CompletableFuture<Game> create(Game game) {
        if (game.getId() == null) {
            return CompletableFuture.supplyAsync(() -> {
                Task<DocumentReference> task = firestore.collection(COLLECTION_NAME)
                        .add(gameToMap(game));

                try {
                    DocumentReference documentReference = Tasks.await(task);

                    return documentReference.getId();
                } catch (Exception e) {
                    throw new CompletionException(e);
                }
            }).thenCompose(this::find);
        } else {
            return CompletableFuture.supplyAsync(() -> {
                Task<Void> task = firestore.collection(COLLECTION_NAME).document(game.getId()).set(gameToMap(game));

                try {
                    Tasks.await(task);

                    return game.getId();
                } catch (Exception e) {
                    throw new CompletionException(e);
                }
            }).thenCompose(this::find);
        }
    }

    @Override
    public CompletableFuture<Game> find(String id) {
        return CompletableFuture.supplyAsync(() -> {
            Task<DocumentSnapshot> task = firestore.collection(COLLECTION_NAME).document(id).get();

            try {
                DocumentSnapshot document = Tasks.await(task);

                if (document.exists()) {
                    return documentToGame(document);
                }

                throw new CompletionException(new Exception("Game not found"));
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        });
    }

    @Override
    public CompletableFuture<Game> update(Game game) {
        return CompletableFuture.supplyAsync(() -> {
            Task<Void> task = firestore.collection(COLLECTION_NAME)
                    .document(game.getId())
                    .update(gameToMap(game));

            try {
                Tasks.await(task);

                return game.getId();
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        }).thenCompose(this::find);
    }

    @Override
    public CompletableFuture<Void> remove(Game game) {
        return CompletableFuture.supplyAsync(() -> {
            Task<Void> task = firestore.collection(COLLECTION_NAME).document(game.getId()).delete();

            try {
                Tasks.await(task);

                return null;
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        });
    }

    @Override
    public CompletableFuture<List<Game>> all() {
        return CompletableFuture.supplyAsync(() -> {
            Task<QuerySnapshot> task = firestore.collection(COLLECTION_NAME).get();

            try {
                QuerySnapshot querySnapshot = Tasks.await(task);

                List<Game> games = new ArrayList<>();
                for (QueryDocumentSnapshot documentSnapshot : querySnapshot) {
                    games.add(documentToGame(documentSnapshot));
                }

                return games;
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        });
    }

    private static Map<String, Object> gameToMap(Game game) {
        Map<String, Object> gameMap = new HashMap<>();

        return gameMap;
    }

    private static Game documentToGame(DocumentSnapshot document) {
        Game game = new Game();

        game.setId(document.getId());

        return game;
    }
}
