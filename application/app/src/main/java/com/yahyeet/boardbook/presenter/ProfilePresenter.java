package com.yahyeet.boardbook.presenter;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.presenter.matchfeed.MatchfeedAdapter;
import com.yahyeet.boardbook.activity.profile.IProfileActivity;
import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.model.entity.User;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ProfilePresenter {

	private MatchfeedAdapter matchfeedAdapter;
	private List<Match> matchDatabase;
	private User user;

	private IProfileActivity profileActivity;

	public ProfilePresenter(IProfileActivity profileActivity, String userId) {
		this.profileActivity = profileActivity;
		try {
			user = BoardbookSingleton.getInstance().getUserHandler().find(userId).get();
		} catch (ExecutionException | InterruptedException e) {
			// TODO: What to do here?
		}
	}

	public String getLoggedInUserName() {
		return BoardbookSingleton.getInstance().getAuthHandler().getLoggedInUser().getName();
	}


	public String getGamesPlayedForLoggedInUser() {
		return "45";
	}

	public double getWinrateQuotaForLoggedInUser() {
		return 0.45;
	}

	public String getWinratePercentageForLoggedInUser() {
		return "40" + "%";
	}

	/**
	 * Makes recyclerView to repopulate its matches with current data
	 */
	public void updateMatchAdapter() {
		matchfeedAdapter.notifyDataSetChanged();
	}

	/**
	 * Creates the necessary structure for populating matches
	 *
	 * @param matchRecyclerView the RecyclerView that will be populated with matches
	 */
	public void enableMatchFeed(RecyclerView matchRecyclerView, Context viewContext) {

		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(viewContext);
		matchRecyclerView.setLayoutManager(layoutManager);
		matchfeedAdapter = new MatchfeedAdapter(viewContext, matchDatabase, user, BoardbookSingleton.getInstance().getStatisticsUtil());
		matchRecyclerView.setAdapter(matchfeedAdapter);
	}

}
