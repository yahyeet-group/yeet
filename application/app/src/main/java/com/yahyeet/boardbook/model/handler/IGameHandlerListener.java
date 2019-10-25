package com.yahyeet.boardbook.model.handler;

import com.yahyeet.boardbook.model.entity.Game;

/**
 * Interface that defines the methods a game handler listener must implement
 */
public interface GameHandlerListener {
	void onAddGame(Game game);

	void onUpdateGame(Game game);

	void onRemoveGame(String id);
}
