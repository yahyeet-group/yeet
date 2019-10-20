package com.yahyeet.boardbook.activity.home.game;

public interface IGameFragment {

	/**
	 * Enables all interactive elements of the activity
	 */
	void enableFragmentInteraction();

	/**
	 * Disables all interactive elements of the activity
	 */
	void disableFragmentInteraction();

	/**
	 * Displays error message of games not loading correctly
	 */
	void displayLoadingFailed();
}
