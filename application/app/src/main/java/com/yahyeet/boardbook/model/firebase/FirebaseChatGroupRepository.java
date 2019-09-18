package com.yahyeet.boardbook.model.firebase;

import com.yahyeet.boardbook.model.entity.ChatGroup;
import com.yahyeet.boardbook.model.repository.IChatGroupRepository;
import com.yahyeet.boardbook.model.repository.RepositoryResultListener;

import java.util.List;

public class FirebaseChatGroupRepository implements IChatGroupRepository {
    @Override
    public void create(ChatGroup group, RepositoryResultListener<ChatGroup> listener) {

    }

    @Override
    public void find(String id, RepositoryResultListener<ChatGroup> listener) {

    }

    @Override
    public void update(ChatGroup group, RepositoryResultListener<ChatGroup> listener) {

    }

    @Override
    public void remove(ChatGroup group, RepositoryResultListener<Void> listener) {

    }

    @Override
    public void all(RepositoryResultListener<List<ChatGroup>> listener) {

    }
}
