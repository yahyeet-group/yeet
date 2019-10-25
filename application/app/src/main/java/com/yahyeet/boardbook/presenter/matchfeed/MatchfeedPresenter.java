package com.yahyeet.boardbook.presenter.matchfeed;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.activity.home.matchfeed.IMatchfeedFragment;
import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.handler.IMatchHandlerListener;
import com.yahyeet.boardbook.presenter.BoardbookSingleton;

import java.util.ArrayList;
import java.util.List;

/**
 * Presenter for the match feed activity
 */
public class MatchfeedPresenter implements IMatchHandlerListener {

	private MatchfeedAdapter matchfeedAdapter;
	private List<Match> matchDatabase = new ArrayList<>();


	// TODO: Remove if never necessary
	private IMatchfeedFragment matchfeedFragment;

	public MatchfeedPresenter(IMatchfeedFragment matchfeedFragment) {
		this.matchfeedFragment = matchfeedFragment;
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

		User loggedIn = BoardbookSingleton.getInstance().getAuthHandler().getLoggedInUser();


		matchDatabase.addAll(loggedIn.getMatches());


		// TODO: This code breaks everything and needs to be reimplemented
		// TODO: If implemented then IMatchfeedFragment needs to extend IFutureIntractable
		/*CompletableFuture.allOf(loggedIn
			.getFriends()
			.stream()
			.map(friend -> BoardbookSingleton.getInstance().getUserHandler()
				.find(friend.getId()).thenApply(populatedFriend -> {
					matchDatabase.addAll(populatedFriend.getMatches());
					return null;
				})).toArray(CompletableFuture[]::new)).thenAccept(nothing -> {
			// Now all are added

		});*/
		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(viewContext);
		matchRecyclerView.setLayoutManager(layoutManager);
		matchfeedAdapter = new MatchfeedAdapter(viewContext, matchDatabase);
		matchRecyclerView.setAdapter(matchfeedAdapter);


	}

	@Override
	public void onAddMatch(Match match) {
		matchDatabase.add(match);
		updateMatchAdapter();
	}

	@Override
	public void onUpdateMatch(Match match) {
		for (int i = 0; i < matchDatabase.size(); i++) {
			if (matchDatabase.get(i).getId().equals(match.getId())) {
				matchDatabase.set(i, match);
			}
		}
		updateMatchAdapter();
	}

	@Override
	public void onRemoveMatch(String id) {
		for (int i = 0; i < matchDatabase.size(); i++) {
			if (matchDatabase.get(i).getId().equals(id)) {
				matchDatabase.remove(i);
				break;
			}
		}
		updateMatchAdapter();
	}
}
