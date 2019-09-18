package com.yahyeet.boardbook.model.firebase;

import com.yahyeet.boardbook.model.entity.ChatMessage;
import com.yahyeet.boardbook.model.repository.IChatMessageRepository;
import com.yahyeet.boardbook.model.repository.RepositoryResultListener;

import java.util.List;

public class FirebaseChatMessageRepository implements IChatMessageRepository {
    @Override
    public void create(ChatMessage message, RepositoryResultListener<ChatMessage> listener) {

    }

    @Override
    public void find(String id, RepositoryResultListener<ChatMessage> listener) {

    }

    @Override
    public void update(ChatMessage message, RepositoryResultListener<ChatMessage> listener) {

    }

    @Override
    public void remove(ChatMessage message, RepositoryResultListener<Void> listener) {

    }

    @Override
    public void all(RepositoryResultListener<List<ChatMessage>> listener) {

    }
}
