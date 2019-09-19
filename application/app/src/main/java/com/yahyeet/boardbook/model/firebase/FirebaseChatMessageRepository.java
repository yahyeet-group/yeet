package com.yahyeet.boardbook.model.firebase;

import com.yahyeet.boardbook.model.entity.ChatMessage;
import com.yahyeet.boardbook.model.repository.IChatMessageRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class FirebaseChatMessageRepository implements IChatMessageRepository {
    @Override
    public CompletableFuture<ChatMessage> create(ChatMessage entity) {
        return null;
    }

    @Override
    public CompletableFuture<ChatMessage> find(String id) {
        return null;
    }

    @Override
    public CompletableFuture<ChatMessage> update(ChatMessage entity) {
        return null;
    }

    @Override
    public CompletableFuture<Void> remove(ChatMessage entity) {
        return null;
    }

    @Override
    public CompletableFuture<List<ChatMessage>> all() {
        return null;
    }
}
