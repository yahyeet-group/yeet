package com.yahyeet.boardbook.model

import com.yahyeet.boardbook.model.handler.AuthHandler
import com.yahyeet.boardbook.model.handler.GameHandler
import com.yahyeet.boardbook.model.handler.MatchHandler
import com.yahyeet.boardbook.model.handler.UserHandler
import com.yahyeet.boardbook.model.util.StatisticsUtil

class Boardbook(val authHandler: AuthHandler,
                val userHandler: UserHandler,
                val gameHandler: GameHandler,
                val matchHandler: MatchHandler
) {

    val statisticsUtil: StatisticsUtil

    init {
        statisticsUtil = StatisticsUtil()
    }
}
