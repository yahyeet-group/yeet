package com.yahyeet.boardbook.model.firebase.repository;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.yahyeet.boardbook.model.entity.ChatGroup;
import com.yahyeet.boardbook.model.repository.IChatGroupRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class FirebaseChatGroupRepository implements IChatGroupRepository {
    private FirebaseFirestore firestore;

    public static final String COLLECTION_NAME = "chatGroups";

    public FirebaseChatGroupRepository(FirebaseFirestore firestore) {
        this.firestore = firestore;
    }

    @Override
    public CompletableFuture<ChatGroup> create(ChatGroup chatGroup) {
        return CompletableFuture.supplyAsync(() -> {
            Task<DocumentReference> task = firestore.collection(COLLECTION_NAME)
                    .add(chatGroupToMap(chatGroup));

            try {
                DocumentReference documentReference = Tasks.await(task);

                return documentReference.getId();
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        }).thenCompose(this::find);
    }

    @Override
    public CompletableFuture<ChatGroup> find(String id) {
        return CompletableFuture.supplyAsync(() -> {
            Task<DocumentSnapshot> task = firestore.collection(COLLECTION_NAME).document(id).get();

            try {
                DocumentSnapshot document = Tasks.await(task);

                if (document.exists()) {
                    return documentToChatGroup(document);
                }

                throw new CompletionException(new Exception("ChatGroup not found"));
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        });
    }

    @Override
    public CompletableFuture<ChatGroup> update(ChatGroup chatGroup) {
        return CompletableFuture.supplyAsync(() -> {
            Task<Void> task = firestore.collection(COLLECTION_NAME)
                    .document(chatGroup.getId())
                    .update(chatGroupToMap(chatGroup));

            try {
                Tasks.await(task);

                return chatGroup.getId();
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        }).thenCompose(this::find);
    }

    @Override
    public CompletableFuture<Void> remove(ChatGroup chatGroup) {
        return CompletableFuture.supplyAsync(() -> {
            Task<Void> task = firestore.collection(COLLECTION_NAME).document(chatGroup.getId()).delete();

            try {
                Tasks.await(task);

                return null;
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        });
    }

    @Override
    public CompletableFuture<List<ChatGroup>> all() {
        return CompletableFuture.supplyAsync(() -> {
            Task<QuerySnapshot> task = firestore.collection(COLLECTION_NAME).get();

            try {
                QuerySnapshot querySnapshot = Tasks.await(task);

                List<ChatGroup> chatGroups = new ArrayList<>();
                for (QueryDocumentSnapshot documentSnapshot : querySnapshot) {
                    chatGroups.add(documentToChatGroup(documentSnapshot));
                }

                return chatGroups;
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        });
    }

    private static Map<String, Object> chatGroupToMap(ChatGroup chatGroup) {
        Map<String, Object> chatGroupMap = new HashMap<>();

        return chatGroupMap;
    }

    private static ChatGroup documentToChatGroup(DocumentSnapshot document) {
        ChatGroup chatGroup = new ChatGroup();

        chatGroup.setId(document.getId());

        return chatGroup;
    }
}
