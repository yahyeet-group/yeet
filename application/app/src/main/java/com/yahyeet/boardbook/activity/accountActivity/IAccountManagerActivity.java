package com.yahyeet.boardbook.activity.accountActivity;

public interface IAccountManagerActivity {

	/**
	 * Starts the homepage and finishes the account manager.
	 */
	void finishAccountManager();

	/**
	 * Enables all interactive elements of the activity
	 */
	void enableManagerInteraction();

	/**
	 * Disables all interactive elements of the activity
	 */
	void disableManagerInteraction();

	/**
	 * Handles where exceptions from presenter should be displayed in login fragment
	 * @param exception contains message and is used to determine where exception should be displayed
	 */
	void loginFailed(Exception exception);

	/**
	 * Handles where exceptions from presenter should be displayed in register fragment
	 * @param exception contains message and is used to determine where exception should be displayed
	 */
	void registerFailed(Exception exception);

}
