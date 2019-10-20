package com.yahyeet.boardbook.activity.accountActivity;

public interface IAccountManager {

	void loginAccount(String email, String password);

	void registerAccount(String email, String password, String username);

	void finishAccountManager();

	void disableManagerInteraction();

	void enableManagerInteraction();
}
