package com.yahyeet.boardbook.model.firebase;

import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.model.repository.IGameRepository;
import com.yahyeet.boardbook.model.repository.RepositoryResultListener;

import java.util.List;

public class FirebaseGameRepository implements IGameRepository {
    @Override
    public void create(Game game, RepositoryResultListener<Game> listener) {

    }

    @Override
    public void find(String id, RepositoryResultListener<Game> listener) {

    }

    @Override
    public void update(Game game, RepositoryResultListener<Game> listener) {

    }

    @Override
    public void remove(Game game, RepositoryResultListener<Void> listener) {

    }

    @Override
    public void all(RepositoryResultListener<List<Game>> listener) {

    }
}
