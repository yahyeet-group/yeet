package com.yahyeet.boardbook.model.firebase;

import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.model.repository.IGameRepository;

public class FirebaseGameRepository implements IGameRepository {
    @Override
    public void Add(Game game) {

    }

    @Override
    public Game Find(String id) {
        // Changed to be able to run code, was return void
        return null;
    }

    @Override
    public void Remove(Game game) {

    }

    @Override
    public void Update(Game game) {

    }
}
