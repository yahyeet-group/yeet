package com.yahyeet.boardbook.model;

import com.yahyeet.boardbook.model.handler.AuthHandler;
import com.yahyeet.boardbook.model.handler.GameHandler;
import com.yahyeet.boardbook.model.handler.MatchHandler;
import com.yahyeet.boardbook.model.handler.UserHandler;
import com.yahyeet.boardbook.model.repository.IGameRepository;
import com.yahyeet.boardbook.model.repository.IMatchRepository;
import com.yahyeet.boardbook.model.repository.IUserRepository;
import com.yahyeet.boardbook.model.service.IAuthService;
import com.yahyeet.boardbook.model.util.StatisticsUtil;

public final class Boardbook {

    private AuthHandler authHandler;

    private UserHandler userHandler;
    private GameHandler gameHandler;
    private MatchHandler matchHandler;

    private StatisticsUtil statisticsUtil;

    public Boardbook(IAuthService authService,
                     IUserRepository userRepository,
                     IGameRepository gameRepository,
                     IMatchRepository matchRepository
    ) {
        userHandler = new UserHandler(userRepository, matchRepository);
        gameHandler = new GameHandler(gameRepository);
        matchHandler = new MatchHandler(matchRepository);
        authHandler = new AuthHandler(authService, userHandler);
        statisticsUtil = new StatisticsUtil();
    }

    public AuthHandler getAuthHandler() {
        return authHandler;
    }

    public UserHandler getUserHandler() {
        return userHandler;
    }

    public GameHandler getGameHandler() {
        return gameHandler;
    }

    public MatchHandler getMatchHandler() {
        return matchHandler;
    }

    public StatisticsUtil getStatisticsUtil() {
        return statisticsUtil;
    }
}
