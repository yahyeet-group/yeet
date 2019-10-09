package com.yahyeet.boardbook.model.firebase.repository;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.model.repository.IMatchRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class FirebaseMatchRepository implements IMatchRepository {
    private FirebaseFirestore firestore;

    public static final String COLLECTION_NAME = "matches";

    public FirebaseMatchRepository(FirebaseFirestore firestore) {
        this.firestore = firestore;
    }

    @Override
    public CompletableFuture<Match> create(Match match) {
        if (match.getId() == null) {
            return CompletableFuture.supplyAsync(() -> {
                Task<DocumentReference> task = firestore.collection(COLLECTION_NAME)
                        .add(matchToMap(match));

                try {
                    DocumentReference documentReference = Tasks.await(task);

                    return documentReference.getId();
                } catch (Exception e) {
                    throw new CompletionException(e);
                }
            }).thenCompose(this::find);
        } else {
            return CompletableFuture.supplyAsync(() -> {
                Task<Void> task = firestore.collection(COLLECTION_NAME).document(match.getId()).set(matchToMap(match));

                try {
                    Tasks.await(task);

                    return match.getId();
                } catch (Exception e) {
                    throw new CompletionException(e);
                }
            }).thenCompose(this::find);
        }
    }

    @Override
    public CompletableFuture<Match> find(String id) {
        return CompletableFuture.supplyAsync(() -> {
            Task<DocumentSnapshot> task = firestore.collection(COLLECTION_NAME).document(id).get();

            try {
                DocumentSnapshot document = Tasks.await(task);

                if (document.exists()) {
                    return documentToMatch(document);
                }

                throw new CompletionException(new Exception("Match not found"));
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        });
    }

    @Override
    public CompletableFuture<Match> update(Match match) {
        return CompletableFuture.supplyAsync(() -> {
            Task<Void> task = firestore.collection(COLLECTION_NAME)
                    .document(match.getId())
                    .update(matchToMap(match));

            try {
                Tasks.await(task);

                return match.getId();
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        }).thenCompose(this::find);
    }

    @Override
    public CompletableFuture<Void> remove(Match match) {
        return CompletableFuture.supplyAsync(() -> {
            Task<Void> task = firestore.collection(COLLECTION_NAME).document(match.getId()).delete();

            try {
                Tasks.await(task);

                return null;
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        });
    }

    @Override
    public CompletableFuture<List<Match>> all() {
        return CompletableFuture.supplyAsync(() -> {
            Task<QuerySnapshot> task = firestore.collection(COLLECTION_NAME).get();

            try {
                QuerySnapshot querySnapshot = Tasks.await(task);

                List<Match> matches = new ArrayList<>();
                for (QueryDocumentSnapshot documentSnapshot : querySnapshot) {
                    matches.add(documentToMatch(documentSnapshot));
                }

                return matches;
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        });
    }

    private static Map<String, Object> matchToMap(Match match) {
        Map<String, Object> matchMap = new HashMap<>();

        return matchMap;
    }

    private static Match documentToMatch(DocumentSnapshot document) {
        Match match = new Match();

        match.setId(document.getId());

        return match;
    }

    @Override
    public CompletableFuture<List<Match>> findMatchesByUserId(String id) {
        return null;
    }
}
