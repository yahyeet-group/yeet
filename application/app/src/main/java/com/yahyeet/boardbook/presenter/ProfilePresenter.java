package com.yahyeet.boardbook.presenter;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.presenter.matchfeed.MatchfeedAdapter;
import com.yahyeet.boardbook.activity.IFutureInteractable;
import com.yahyeet.boardbook.model.handler.UserHandler;
import com.yahyeet.boardbook.activity.profile.IProfileActivity;
import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.model.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Presenter for the profile activity
 */
public class ProfilePresenter extends FindOnePresenter<User, UserHandler> {

	private MatchfeedAdapter matchfeedAdapter;
	private List<Match> matchDatabase = new ArrayList<>();
	private User user;

	private IProfileActivity profileActivity;

	public ProfilePresenter(IProfileActivity profileActivity, String userId) {
		super((IFutureInteractable) profileActivity);
		this.profileActivity = profileActivity;

		user = BoardbookSingleton.getInstance().getAuthHandler().getLoggedInUser();
		findEntity(BoardbookSingleton.getInstance().getUserHandler(), userId,
			UserHandler.generatePopulatorConfig(false, true));
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

	@Override
	protected void onEntityFound(User entity) {
		matchDatabase.clear();
		matchDatabase.addAll(entity.getMatches());
		user = entity;

		profileActivity.enableAdapter();
		updateMatchAdapter();
	}
}
