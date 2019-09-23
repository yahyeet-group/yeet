package com.yahyeet.boardbook.model.firebase;

import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.model.repository.IMatchRepository;

public class FirebaseMatchRepository implements IMatchRepository {
    @Override
    public void Add(Match match) {

    }

    @Override
    public Match Find(String id) {
        // Changed to be able to run code, was return void
        return null;
    }

    @Override
    public void Remove(Match match) {

    }

    @Override
    public void Update(Match match) {

    }
}
