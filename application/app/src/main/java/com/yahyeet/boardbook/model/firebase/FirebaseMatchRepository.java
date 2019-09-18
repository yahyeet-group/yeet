package com.yahyeet.boardbook.model.firebase;

import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.model.repository.IMatchRepository;
import com.yahyeet.boardbook.model.repository.RepositoryResultListener;

import java.util.List;

public class FirebaseMatchRepository implements IMatchRepository {
    @Override
    public void create(Match match, RepositoryResultListener<Match> listener) {
    }

    @Override
    public void find(String id, RepositoryResultListener<Match> listener) {
    }

    @Override
    public void update(Match match, RepositoryResultListener<Match> listener) {
    }

    @Override
    public void remove(Match match, RepositoryResultListener<Void> listener) {
    }

    @Override
    public void all(RepositoryResultListener<List<Match>> listener) {

    }
}
