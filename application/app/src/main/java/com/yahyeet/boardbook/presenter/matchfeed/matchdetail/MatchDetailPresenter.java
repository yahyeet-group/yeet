package com.yahyeet.boardbook.presenter.matchfeed.matchdetail;

import android.content.Context;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.activity.IFutureInteractable;
import com.yahyeet.boardbook.activity.home.matchfeed.matchdetail.IMatchDetailActivity;
import com.yahyeet.boardbook.model.entity.MatchPlayer;
import com.yahyeet.boardbook.model.handler.MatchHandler;
import com.yahyeet.boardbook.presenter.BoardbookSingleton;
import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.presenter.FindOnePresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Presenter for the match detail activity
 */
public class MatchDetailPresenter extends FindOnePresenter<Match, MatchHandler> {


	private IMatchDetailActivity matchDetailActivity;
	private MatchPlayerAdapter matchPlayerAdapter;

	private List<MatchPlayer> matchPlayers = new ArrayList<>();

	public MatchDetailPresenter(IMatchDetailActivity matchDetailActivity, String matchID) {
		super((IFutureInteractable) matchDetailActivity);
		this.matchDetailActivity = matchDetailActivity;

		findEntity(BoardbookSingleton.getInstance().getMatchHandler(), matchID,
			MatchHandler.generatePopulatorConfig(true, true));


	}


	private void setMatchDetailName() {
		matchDetailActivity.setGameName("Match of " + getEntity().getGame().getName());
	}


	/**
	 * Initiates the adapter for a matchplayerRecyclerView
	 *
	 * @param matchplayerRecyclerView view that will receive new adapter
	 * @param viewContext             structure of current view
	 */
	public void enableMatchplayerAdapter(RecyclerView matchplayerRecyclerView, Context viewContext) {

		RecyclerView.LayoutManager layoutManager = new GridLayoutManager(viewContext, 1);
		matchPlayerAdapter = new MatchPlayerAdapter(matchPlayers);

		matchplayerRecyclerView.setLayoutManager(layoutManager);
		matchplayerRecyclerView.setAdapter(matchPlayerAdapter);
	}

	@Override
	protected void onEntityFound(Match entity) {
		setMatchDetailName();

		matchPlayers.clear();
		matchPlayers.addAll(sortByWin(entity.getMatchPlayers()));

	}

	private List<MatchPlayer> sortByWin(List<MatchPlayer> unsortedPlayers) {

		return unsortedPlayers
			.stream()
			.sorted((left, right) -> -Boolean.compare(left.getWin(), right.getWin()))
			.collect(Collectors.toList());
	}
}
