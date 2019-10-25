package com.yahyeet.boardbook.presenter.matchfeed;

import android.content.Context;
import android.os.Looper;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.activity.IFutureInteractable;
import com.yahyeet.boardbook.activity.home.matchfeed.IMatchfeedFragment;
import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.handler.IMatchHandlerListener;
import com.yahyeet.boardbook.model.handler.UserHandler;
import com.yahyeet.boardbook.presenter.BoardbookSingleton;
import com.yahyeet.boardbook.presenter.FindOnePresenter;

import java.util.ArrayList;
import java.util.List;


/**
 * Presenter for the match feed activity
 */
public class MatchfeedPresenter extends FindOnePresenter<User, UserHandler> implements IMatchHandlerListener {

	private MatchfeedAdapter matchfeedAdapter;
	private List<Match> matchDatabase = new ArrayList<>();


	// TODO: Remove if never necessary
	private IMatchfeedFragment matchfeedFragment;

	public MatchfeedPresenter(IMatchfeedFragment matchfeedFragment) {
		super((IFutureInteractable) matchfeedFragment);
		this.matchfeedFragment = matchfeedFragment;

		BoardbookSingleton.getInstance().getMatchHandler().addListener(this);

		updateMatchDatabase();
	}

	/**
	 * Makes recyclerView to repopulate its matches with current data
	 */
	public void updateAdapter() {
		new android.os.Handler(Looper.getMainLooper())
			.post(() -> matchfeedAdapter.notifyDataSetChanged());

	}

	/**
	 * Creates the necessary structure for populating matches
	 *
	 * @param matchRecyclerView the RecyclerView that will be populated with matches
	 */
	public void enableMatchFeed(RecyclerView matchRecyclerView, Context viewContext) {
		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(viewContext);
		matchfeedAdapter = new MatchfeedAdapter(viewContext, matchDatabase);

		matchRecyclerView.setLayoutManager(layoutManager);
		matchRecyclerView.setAdapter(matchfeedAdapter);


	}

	@Override
	public void onAddMatch(Match match) {
		BoardbookSingleton.getInstance().getMatchHandler().find(match.getId()).thenAccept(foundMatch -> {
			if (foundMatch.getMatchPlayerByUser(BoardbookSingleton.getInstance().getAuthHandler().getLoggedInUser()) != null)
				matchDatabase.add(foundMatch);
			updateAdapter();
		});

	}

	@Override
	public void onUpdateMatch(Match match) {
		for (int i = 0; i < matchDatabase.size(); i++) {
			if (matchDatabase.get(i).getId().equals(match.getId())) {
				matchDatabase.set(i, match);
			}
		}
		updateAdapter();
	}

	@Override
	public void onRemoveMatch(String id) {
		for (int i = 0; i < matchDatabase.size(); i++) {
			if (matchDatabase.get(i).getId().equals(id)) {
				matchDatabase.remove(i);
				break;
			}
		}
		updateAdapter();
	}

	private void updateMatchDatabase() {
		findEntity(
			BoardbookSingleton
				.getInstance()
				.getUserHandler(),
			BoardbookSingleton
				.getInstance()
				.getAuthHandler()
				.getLoggedInUser()
				.getId(),
			UserHandler
				.generatePopulatorConfig(true, true)
		);
	}

	@Override
	protected void onEntityFound(User entity) {
		matchDatabase.clear();
		matchDatabase.addAll(entity.getMatches());
		updateAdapter();
	}


}
