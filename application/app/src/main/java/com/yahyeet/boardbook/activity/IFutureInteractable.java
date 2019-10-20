package com.yahyeet.boardbook.activity;

public interface IFutureInteractable {

	/**
	 * Enables all interactive elements of the activity
	 */
	void enableViewInteraction();

	/**
	 * Disables all interactive elements of the activity
	 */
	void disableViewInteraction();

	/**
	 * Displays error message of games not loading correctly
	 */
	void displayLoadingFailed();
}
