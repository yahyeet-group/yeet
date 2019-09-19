package com.yahyeet.boardbook.model.firebase;

import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.model.repository.IGameRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class FirebaseGameRepository implements IGameRepository {
    @Override
    public CompletableFuture<Game> create(Game entity) {
        return null;
    }

    @Override
    public CompletableFuture<Game> find(String id) {
        return null;
    }

    @Override
    public CompletableFuture<Game> update(Game entity) {
        return null;
    }

    @Override
    public CompletableFuture<Void> remove(Game entity) {
        return null;
    }

    @Override
    public CompletableFuture<List<Game>> all() {
        return null;
    }
}
