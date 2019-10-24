package com.yahyeet.boardbook.presenter.matchfeed.matchdetail;

import android.content.Context;
import android.content.res.Resources;
import android.os.Looper;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.activity.IFutureInteractable;
import com.yahyeet.boardbook.activity.home.matchfeed.matchdetail.IMatchDetailActivity;
import com.yahyeet.boardbook.model.entity.MatchPlayer;
import com.yahyeet.boardbook.model.handler.MatchHandler;
import com.yahyeet.boardbook.model.handler.MatchHandlerListener;
import com.yahyeet.boardbook.presenter.BoardbookSingleton;
import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.presenter.FindOnePresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class MatchDetailPresenter extends FindOnePresenter<Match, MatchHandler> implements MatchHandlerListener {

	private IMatchDetailActivity matchDetailActivity;
	private MatchPlayerAdapter matchPlayerAdapter;

	private List<MatchPlayer> matchPlayers = new ArrayList<>();

	public MatchDetailPresenter(IMatchDetailActivity matchDetailActivity, String matchID) {
		super((IFutureInteractable) matchDetailActivity);
		this.matchDetailActivity = matchDetailActivity;

		BoardbookSingleton.getInstance().getMatchHandler().addListener(this);

		findEntity(BoardbookSingleton.getInstance().getMatchHandler(), matchID);


	}


	private void setMatchDetailName() {
		matchDetailActivity.setGameName("Game of " + getEntity().getGame().getName());
	}


	/**
	 * Initiates the adapter for a matchplayerRecyclerView
	 *
	 * @param matchplayerRecyclerView view that will receive new adapter
	 * @param viewContext             structure of current view
	 */
	public void enableMatchplayerAdapter(RecyclerView matchplayerRecyclerView, Context viewContext, Resources resources) {

		RecyclerView.LayoutManager layoutManager = new GridLayoutManager(viewContext, 1);
		matchPlayerAdapter = new MatchPlayerAdapter(matchPlayers, resources);

		matchplayerRecyclerView.setLayoutManager(layoutManager);
		matchplayerRecyclerView.setAdapter(matchPlayerAdapter);
	}

	@Override
	protected void onEntityFound(Match entity) {
		setMatchDetailName();

		matchPlayers.clear();
		matchPlayers.addAll(sortByWin(entity.getMatchPlayers()));
		matchDetailActivity.initiateMatchDetailList();

	}

	private List<MatchPlayer> sortByWin(List<MatchPlayer> unsortedPlayers){

		return unsortedPlayers
			.stream()
			.sorted((left, right) -> {
				if(left.getWin() && !right.getWin())
					return 1;
				else if(left.getWin() && right.getWin())
					return 0;
				else
					return -1;
			})
			.collect(Collectors.toList());
	}

	@Override
	public void onAddMatch(Match match) {

	}

	@Override
	public void onUpdateMatch(Match match) {

	}

	@Override
	public void onRemoveMatch(String id) {

	}
}
