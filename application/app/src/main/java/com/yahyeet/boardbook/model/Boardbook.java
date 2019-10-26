package com.yahyeet.boardbook.model;

import com.yahyeet.boardbook.model.handler.AuthHandler;
import com.yahyeet.boardbook.model.handler.GameHandler;
import com.yahyeet.boardbook.model.handler.MatchHandler;
import com.yahyeet.boardbook.model.handler.UserHandler;
import com.yahyeet.boardbook.model.util.StatisticsUtil;

/**
 * The facade class of the model
 */
public final class Boardbook {
	private AuthHandler authHandler;
	private UserHandler userHandler;
	private GameHandler gameHandler;
	private MatchHandler matchHandler;

	private StatisticsUtil statisticsUtil;

	public Boardbook(AuthHandler authHandler,
									 UserHandler userHandler,
									 GameHandler gameHandler,
									 MatchHandler matchHandler
	) {
		this.userHandler = userHandler;
		this.gameHandler = gameHandler;
		this.matchHandler = matchHandler;
		this.authHandler = authHandler;
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
