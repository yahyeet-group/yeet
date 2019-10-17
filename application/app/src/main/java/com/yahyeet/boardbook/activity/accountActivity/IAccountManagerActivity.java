package com.yahyeet.boardbook.activity.accountActivity;

public interface IAccountManagerActivity {

	void finishAccountManager();

	void disableManagerInteraction();

	void enableManagerInteraction();

	void loginFailed(Exception e);

	void registerFailed(Exception e);

}
