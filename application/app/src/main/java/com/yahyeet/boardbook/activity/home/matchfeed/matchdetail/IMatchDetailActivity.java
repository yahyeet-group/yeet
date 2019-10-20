package com.yahyeet.boardbook.activity.home.matchfeed.matchdetail;


public interface IMatchDetailActivity {

	void setGameName(String name);

	void initiateMatchDetailList();

	void enableViewInteraction();

	void disableViewInteraction();

	void displayLoadingFailed();
}
