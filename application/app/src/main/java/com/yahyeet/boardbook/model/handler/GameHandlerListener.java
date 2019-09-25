package com.yahyeet.boardbook.model.handler;

import com.yahyeet.boardbook.model.entity.Game;

public interface GameHandlerListener {
    void onAddGame(Game game);

    void onUpdateGame(Game game);

    void onRemoveGame(Game game);
}
