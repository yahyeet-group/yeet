package com.yahyeet.boardbook.model.handler

import com.yahyeet.boardbook.model.entity.Game

interface GameHandlerListener {
    fun onAddGame(game: Game)

    fun onUpdateGame(game: Game)

    fun onRemoveGame(game: Game)
}
