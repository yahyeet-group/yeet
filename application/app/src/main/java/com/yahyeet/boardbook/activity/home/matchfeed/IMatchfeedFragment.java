package com.yahyeet.boardbook.activity.home.matchfeed;

/**
 * Abstract the match feed
 */
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
