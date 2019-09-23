package com.yahyeet.boardbook.model.firebase;

import com.yahyeet.boardbook.model.entity.ChatMessage;
import com.yahyeet.boardbook.model.repository.IChatMessageRepository;

public class FirebaseChatMessageRepository implements IChatMessageRepository {
    @Override
    public void Add(ChatMessage message) {

    }

    @Override
    public ChatMessage Find(String id) {
        // Changed to be able to run code, was return void
        return null;
    }

    @Override
    public void Remove(ChatMessage message) {

    }

    @Override
    public void Update(ChatMessage message) {

    }
}
