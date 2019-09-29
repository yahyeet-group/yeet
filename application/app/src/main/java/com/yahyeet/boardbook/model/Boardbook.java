package com.yahyeet.boardbook.model;

import com.yahyeet.boardbook.model.handler.AuthHandler;
import com.yahyeet.boardbook.model.handler.ChatGroupHandler;
import com.yahyeet.boardbook.model.handler.ChatMessageHandler;
import com.yahyeet.boardbook.model.handler.GameHandler;
import com.yahyeet.boardbook.model.handler.MatchHandler;
import com.yahyeet.boardbook.model.handler.UserHandler;
import com.yahyeet.boardbook.model.repository.IChatGroupRepository;
import com.yahyeet.boardbook.model.repository.IChatMessageRepository;
import com.yahyeet.boardbook.model.repository.IGameRepository;
import com.yahyeet.boardbook.model.repository.IMatchRepository;
import com.yahyeet.boardbook.model.repository.IUserRepository;
import com.yahyeet.boardbook.model.service.IAuthService;

public final class Boardbook {

    private AuthHandler authHandler;

    private UserHandler userHandler;
    private GameHandler gameHandler;
    private MatchHandler matchHandler;
    private ChatGroupHandler chatGroupHandler;
    private ChatMessageHandler chatMessageHandler;

    private boolean TEST_FLAG = false;

    public Boardbook(IAuthService authService,
                     UserHandler userHandler,
                     IGameRepository gameRepository,
                     IMatchRepository matchRepository,
                     IChatGroupRepository chatGroupRepository,
                     IChatMessageRepository chatMessageRepository) {

        if(TEST_FLAG = false) {
            this.userHandler = userHandler;
            gameHandler = new GameHandler(gameRepository);
            matchHandler = new MatchHandler(matchRepository);
            chatGroupHandler = new ChatGroupHandler(chatGroupRepository);
            chatMessageHandler = new ChatMessageHandler(chatMessageRepository);

            authHandler = new AuthHandler(authService, userHandler);
        }
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

    public ChatGroupHandler getChatGroupHandler() {
        return chatGroupHandler;
    }

    public ChatMessageHandler getChatMessageHandler() {
        return chatMessageHandler;
    }
}