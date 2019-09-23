package com.yahyeet.boardbook.model.firebase;

import com.yahyeet.boardbook.model.entity.ChatGroup;
import com.yahyeet.boardbook.model.repository.IChatGroupRepository;

public class FirebaseChatGroupRepository implements IChatGroupRepository {
    @Override
    public void Add(ChatGroup group) {

    }

    @Override
    public ChatGroup Find(String id) {
        // Changed to be able to run code, was return void
        return null;
    }

    @Override
    public void Remove(ChatGroup group) {

    }

    @Override
    public void Update(ChatGroup group) {

    }
}
