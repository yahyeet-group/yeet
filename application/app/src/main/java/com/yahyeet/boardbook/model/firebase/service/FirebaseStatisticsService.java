package com.yahyeet.boardbook.model.firebase.service;

import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.handler.MatchHandler;
import com.yahyeet.boardbook.model.service.IStatisticsService;

import java.util.List;

public class FirebaseStatisticsService implements IStatisticsService {

    private MatchHandler matchHandler;

    public FirebaseStatisticsService(MatchHandler matchHandler){
        this.matchHandler = matchHandler;
    }

    @Override
    public double getWinrateForUser(User user, List<Match> matches) {
        return 0;
    }

    @Override
    public double getWinrateForUserAndGame(User user, Game game) {
        return 0;
    }
}
