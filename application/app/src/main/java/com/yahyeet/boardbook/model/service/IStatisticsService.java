package com.yahyeet.boardbook.model.service;

import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.model.entity.User;

import java.util.List;

public interface IStatisticsService {
    double getWinrateForUser(User user, List<Match> matches);
    double getWinrateForUserAndGame(User user, Game game);
}
