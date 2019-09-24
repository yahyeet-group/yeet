package com.yahyeet.boardbook.model.firebase;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.yahyeet.boardbook.model.entity.ChatMessage;
import com.yahyeet.boardbook.model.repository.IChatMessageRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class FirebaseChatMessageRepository implements IChatMessageRepository {
    private FirebaseFirestore firestore;

    public static final String COLLECTION_NAME = "chat_messages";

    public FirebaseChatMessageRepository(FirebaseFirestore firestore) {
        this.firestore = firestore;
    }

    @Override
    public CompletableFuture<ChatMessage> create(ChatMessage chatMessage) {
        return CompletableFuture.supplyAsync(() -> {
            Task<DocumentReference> task = firestore.collection(COLLECTION_NAME)
                    .add(chatMessageToMap(chatMessage));

            try {
                DocumentReference documentReference = Tasks.await(task);

                return documentReference.getId();
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        }).thenCompose(this::find);
    }

    @Override
    public CompletableFuture<ChatMessage> find(String id) {
        return CompletableFuture.supplyAsync(() -> {
            Task<DocumentSnapshot> task = firestore.collection(COLLECTION_NAME).document(id).get();

            try {
                DocumentSnapshot document = Tasks.await(task);

                if (document.exists()) {
                    return documentToChatMessage(document);
                }

                throw new CompletionException(new Exception("ChatMessage not found"));
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        });
    }

    @Override
    public CompletableFuture<ChatMessage> update(ChatMessage chatMessage) {
        return CompletableFuture.supplyAsync(() -> {
            Task<Void> task = firestore.collection(COLLECTION_NAME)
                    .document(chatMessage.getId())
                    .update(chatMessageToMap(chatMessage));

            try {
                Tasks.await(task);

                return chatMessage.getId();
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        }).thenCompose(this::find);
    }

    @Override
    public CompletableFuture<Void> remove(ChatMessage chatMessage) {
        return CompletableFuture.supplyAsync(() -> {
            Task<Void> task = firestore.collection(COLLECTION_NAME).document(chatMessage.getId()).delete();

            try {
                Tasks.await(task);

                return null;
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        });
    }

    @Override
    public CompletableFuture<List<ChatMessage>> all() {
        return CompletableFuture.supplyAsync(() -> {
            Task<QuerySnapshot> task = firestore.collection(COLLECTION_NAME).get();

            try {
                QuerySnapshot querySnapshot = Tasks.await(task);

                List<ChatMessage> chatMessages = new ArrayList<>();
                for (QueryDocumentSnapshot documentSnapshot : querySnapshot) {
                    chatMessages.add(documentToChatMessage(documentSnapshot));
                }

                return chatMessages;
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        });
    }

    private static Map<String, Object> chatMessageToMap(ChatMessage chatMessage) {
        Map<String, Object> chatMessageMap = new HashMap<>();

        return chatMessageMap;
    }

    private static ChatMessage documentToChatMessage(DocumentSnapshot document) {
        ChatMessage chatMessage = new ChatMessage();

        chatMessage.setId(document.getId());

        return chatMessage;
    }
}
