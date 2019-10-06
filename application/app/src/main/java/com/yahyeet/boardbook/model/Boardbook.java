package com.yahyeet.boardbook.model;

import com.yahyeet.boardbook.model.handler.AuthHandler;
import com.yahyeet.boardbook.model.handler.GameHandler;
import com.yahyeet.boardbook.model.handler.MatchHandler;
import com.yahyeet.boardbook.model.handler.UserHandler;
import com.yahyeet.boardbook.model.repository.IGameRepository;
import com.yahyeet.boardbook.model.repository.IMatchRepository;
import com.yahyeet.boardbook.model.repository.IUserRepository;
import com.yahyeet.boardbook.model.service.IAuthService;

public final class Boardbook {

    private AuthHandler authHandler;

    private UserHandler userHandler;
    private GameHandler gameHandler;
    private MatchHandler matchHandler;

    public Boardbook(IAuthService authService,
                     IUserRepository userRepository,
                     IGameRepository gameRepository,
                     IMatchRepository matchRepository
    ) {
        userHandler = new UserHandler(userRepository);
        gameHandler = new GameHandler(gameRepository);
        matchHandler = new MatchHandler(matchRepository);
        authHandler = new AuthHandler(authService, userHandler);
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
}
