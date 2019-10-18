package com.yahyeet.boardbook.activity.home.matchfeed;

public interface IMatchfeedFragment {


	/**
	 * Initiates recyclerView of matches with a adapter
	 */
	void enableMatchFeed();

	/**
	 * Orders adapter of matches to repopulate itself
	 */
	void repopulateMatchFeed();

}
