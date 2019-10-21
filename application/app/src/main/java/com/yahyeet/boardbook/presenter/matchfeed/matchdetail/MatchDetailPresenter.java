package com.yahyeet.boardbook.presenter.matchfeed.matchdetail;

import android.content.Context;
import android.content.res.Resources;
import android.os.Looper;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.activity.home.matchfeed.matchdetail.IMatchDetailActivity;
import com.yahyeet.boardbook.presenter.BoardbookSingleton;
import com.yahyeet.boardbook.model.entity.Match;



public class MatchDetailPresenter {

	private IMatchDetailActivity matchDetailActivity;
	private MatchPlayerAdapter matchPlayerAdapter;
	private Match match;

	public MatchDetailPresenter(IMatchDetailActivity matchDetailActivity, String matchID) {
		this.matchDetailActivity = matchDetailActivity;


		matchDetailActivity.disableViewInteraction();
		BoardbookSingleton.getInstance().getMatchHandler().find(matchID).thenAccept(foundMatch -> {
			match = foundMatch;

			new android.os.Handler(Looper.getMainLooper()).post(() -> {
				initiateGameDetail();
				matchDetailActivity.initiateMatchDetailList();
				matchDetailActivity.enableViewInteraction();
			});

		}).exceptionally(e -> {
			e.printStackTrace();
			return null;
		});




	}

	public void initiateGameDetail() {
		matchDetailActivity.setGameName("Game of " + match.getGame().getName());
	}

	/**
	 * Makes recyclerView to repopulate its matches with current data
	 */
	public void updateMatchplayerAdapter() {
		matchPlayerAdapter.notifyDataSetChanged();
	}

	/**
	 * Initiates the adapter for a matchplayerRecyclerView
	 *
	 * @param matchplayerRecyclerView view that will receive new adapter
	 * @param viewContext             structure of current view
	 */
	public void enableMatchplayerAdapter(RecyclerView matchplayerRecyclerView, Context viewContext, Resources resources) {

		RecyclerView.LayoutManager layoutManager = new GridLayoutManager(viewContext, 1);

		matchplayerRecyclerView.setLayoutManager(layoutManager);

		matchPlayerAdapter = new MatchPlayerAdapter(match.getMatchPlayers(), resources);
		matchplayerRecyclerView.setAdapter(matchPlayerAdapter);
	}


}
