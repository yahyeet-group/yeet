package com.yahyeet.boardbook.presenter.game.gamedetail;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.activity.home.game.gamedetail.IGameDetailActivity;
import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.presenter.game.gamedetail.GameDetailTeamAdapter;
import com.yahyeet.boardbook.presenter.BoardbookSingleton;

import java.util.concurrent.ExecutionException;

public class GameDetailPresenter {

	private IGameDetailActivity gameDetailActivity;
	private GameDetailTeamAdapter teamAdapter;
	private Game game;

	public GameDetailPresenter(IGameDetailActivity gameDetailActivity, String gameID) {
		this.gameDetailActivity = gameDetailActivity;
		try {
			game = BoardbookSingleton.getInstance().getGameHandler().find(gameID).get();
		} catch (ExecutionException | InterruptedException e) {
			// TODO: What to do here?
		}
	}

	public void initiateGameDetail() {
		gameDetailActivity.setGameName(game.getName());
		gameDetailActivity.setGameDescription(game.getDescription());
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

		teamAdapter = new GameDetailTeamAdapter(game.getTeams());
		teamRecyclerView.setAdapter(teamAdapter);
	}
}