package com.yahyeet.boardbook.model.firebase;

import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.model.repository.IMatchRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class FirebaseMatchRepository implements IMatchRepository {
    @Override
    public CompletableFuture<Match> create(Match entity) {
        return null;
    }

    @Override
    public CompletableFuture<Match> find(String id) {
        return null;
    }

    @Override
    public CompletableFuture<Match> update(Match entity) {
        return null;
    }

    @Override
    public CompletableFuture<Void> remove(Match entity) {
        return null;
    }

    @Override
    public CompletableFuture<List<Match>> all() {
        return null;
    }
}
