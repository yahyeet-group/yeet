package com.yahyeet.boardbook.presenter.matchfeed.matchdetail;

import android.content.Context;
import android.content.res.Resources;
import android.os.Looper;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.activity.IFutureInteractable;
import com.yahyeet.boardbook.activity.home.matchfeed.matchdetail.IMatchDetailActivity;
import com.yahyeet.boardbook.presenter.BoardbookSingleton;
import com.yahyeet.boardbook.model.entity.Match;

/**
 * Presenter for the match detail activity
 */
public class MatchDetailPresenter {

	private IMatchDetailActivity matchDetailActivity;
	private MatchPlayerAdapter matchPlayerAdapter;
	private Match match;

	public MatchDetailPresenter(IMatchDetailActivity matchDetailActivity, String matchID) {
		this.matchDetailActivity = matchDetailActivity;


		if(matchDetailActivity instanceof IFutureInteractable){

			IFutureInteractable futureDetail = (IFutureInteractable) matchDetailActivity;

			futureDetail.disableViewInteraction();
			BoardbookSingleton.getInstance().getMatchHandler().find(matchID).thenAccept(foundMatch -> {
				match = foundMatch;

				new android.os.Handler(Looper.getMainLooper()).post(() -> {
					setMatchDetailName();
					matchDetailActivity.initiateMatchDetailList();
					futureDetail.enableViewInteraction();
				});

			}).exceptionally(e -> {
				e.printStackTrace();
				futureDetail.displayLoadingFailed();
				return null;
			});
		}
		else{
			throw new IllegalArgumentException("Activity not instance of IFutureIntractable");
		}

	}


	private void setMatchDetailName() {
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
