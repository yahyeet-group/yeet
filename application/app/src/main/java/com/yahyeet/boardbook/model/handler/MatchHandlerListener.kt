package com.yahyeet.boardbook.model.handler

import com.yahyeet.boardbook.model.entity.Match

interface MatchHandlerListener {
    fun onAddMatch(match: Match)

    fun onUpdateMatch(match: Match)

    fun onRemoveMatch(match: Match)
}
