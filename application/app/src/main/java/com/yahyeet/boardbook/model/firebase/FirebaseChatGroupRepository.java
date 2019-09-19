package com.yahyeet.boardbook.model.firebase;

import com.yahyeet.boardbook.model.entity.ChatGroup;
import com.yahyeet.boardbook.model.repository.IChatGroupRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class FirebaseChatGroupRepository implements IChatGroupRepository {
    @Override
    public CompletableFuture<ChatGroup> create(ChatGroup entity) {
        return null;
    }

    @Override
    public CompletableFuture<ChatGroup> find(String id) {
        return null;
    }

    @Override
    public CompletableFuture<ChatGroup> update(ChatGroup entity) {
        return null;
    }

    @Override
    public CompletableFuture<Void> remove(ChatGroup entity) {
        return null;
    }

    @Override
    public CompletableFuture<List<ChatGroup>> all() {
        return null;
    }
}
