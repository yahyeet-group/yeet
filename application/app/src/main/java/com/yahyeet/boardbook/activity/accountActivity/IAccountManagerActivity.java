package com.yahyeet.boardbook.activity.accountActivity;

public interface IAccountManagerActivity {

	void finishAccountManager();

	void disableManagerInteraction();

	void enableManagerInteraction();

	void toastLoginFailed();

	void toastRegisterFailed();
}
