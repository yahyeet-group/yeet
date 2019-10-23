package com.yahyeet.boardbook.activity.home.matchfeed

interface IMatchfeedFragment {


    /**
     * Initiates recyclerView of matches with a adapter
     */
    fun enableMatchFeed()

    /**
     * Orders adapter of matches to repopulate itself
     */
    fun repopulateMatchFeed()

}
