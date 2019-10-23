package com.yahyeet.boardbook.presenter.game.gamedetail;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.activity.IFutureInteractable;
import com.yahyeet.boardbook.activity.home.game.gamedetail.IGameDetailActivity;
import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.model.entity.GameRole;
import com.yahyeet.boardbook.model.entity.GameTeam;
import com.yahyeet.boardbook.model.handler.GameHandler;
import com.yahyeet.boardbook.presenter.BoardbookSingleton;
import com.yahyeet.boardbook.presenter.OneEntityPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class GameDetailPresenter extends OneEntityPresenter<Game, GameHandler> {

	private IGameDetailActivity gameDetailActivity;
	private GameDetailAdapter teamAdapter;

	private List<GameTeam> teams = new ArrayList<>();
	private List<List<GameRole>> roleLists = new ArrayList<>();

	public GameDetailPresenter(IGameDetailActivity gameDetailActivity, String gameID) {
		super((IFutureInteractable) gameDetailActivity);
		this.gameDetailActivity = gameDetailActivity;

		findEntity(BoardbookSingleton.getInstance().getGameHandler(), gameID);

	}

	public void initiateGameDetail() {
		gameDetailActivity.setGameName(getEntity().getName());
		gameDetailActivity.setGameDescription(getEntity().getDescription());
		// TODO: Add rules to games, String field
		gameDetailActivity.setGameRules("");
	}

	/**
	 * Makes recyclerView to repopulate its matches with current data
	 */
	public void updateTeamAdapter() {
		teamAdapter.notifyDataSetChanged();
	}

	/**
	 * Creates the necessary structure for populating teams
	 *
	 * @param teamRecyclerView the RecyclerView that will be populated with teams
	 */
	public void enableTeamList(RecyclerView teamRecyclerView, Context viewContext) {

		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(viewContext);
		teamRecyclerView.setLayoutManager(layoutManager);

		teamAdapter = new GameDetailAdapter(teams, roleLists);
		teamRecyclerView.setAdapter(teamAdapter);
	}

	@Override
	protected void onEntityFound(Game entity) {
		initiateGameDetail();

		teams.clear();
		teams.addAll(entity.getTeams());

		getEntity().getTeams().forEach(team -> {
			roleLists.add(team.getRoles());
		});
	}
}
