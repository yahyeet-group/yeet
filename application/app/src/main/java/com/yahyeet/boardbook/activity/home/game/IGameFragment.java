package com.yahyeet.boardbook.activity.home.game;


public interface IGameFragment{

	void enableViewInteraction();

	void disableViewInteraction();

	/**
	 * Displays error message of games not loading correctly
	 */
	void displayLoadingFailed();
}
