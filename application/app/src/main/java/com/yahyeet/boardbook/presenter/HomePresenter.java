package com.yahyeet.boardbook.presenter;

import android.content.Context;
import android.content.Intent;

import com.yahyeet.boardbook.activity.home.IHomeActivity;
import com.yahyeet.boardbook.activity.profile.ProfileActivity;
import com.yahyeet.boardbook.activity.account.AccountManagerActivity;

public class HomePresenter {

	private IHomeActivity homeActivity;

	public HomePresenter(IHomeActivity homeActivity) {
		this.homeActivity = homeActivity;
	}


	public void startLoggedInUserProfile(){
		Context context = (Context) homeActivity;
		Intent startProfile = new Intent( context, ProfileActivity.class);
		startProfile.putExtra("UserId", BoardbookSingleton.getInstance().getAuthHandler().getLoggedInUser().getId());
		context.startActivity(startProfile);
	}

	public void logOut(){
		BoardbookSingleton.getInstance().getAuthHandler().logout();
		Context context = (Context) homeActivity;
		Intent startLogin = new Intent( context, AccountManagerActivity.class);
		context.startActivity(startLogin);
	}
}
