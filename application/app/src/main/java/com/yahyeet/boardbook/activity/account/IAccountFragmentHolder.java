package com.yahyeet.boardbook.activity.account;

/**
 * Binds events from fragments to the activity so that the fragments does not depend on the parents presenter.
 */
public interface IAccountFragmentHolder {


	/**
	 * Method tries to log into app given valid parameters
	 *
	 * @param email    of the account trying to log in
	 * @param password of the account trying to log in
	 */
	void loginAccount(String email, String password);

	/**
	 * Method tries to create a new account from parameters and log in with it
	 *
	 * @param email    of the new account
	 * @param password of the new account
	 * @param username of the new account
	 */
	void registerAccount(String email, String password, String username);
}
