package com.yahyeet.boardbook.presenter.game.gamedetail;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.activity.home.game.gamedetail.IGameDetailActivity;
import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.model.entity.GameRole;
import com.yahyeet.boardbook.model.entity.GameTeam;
import com.yahyeet.boardbook.presenter.BoardbookSingleton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class GameDetailPresenter {

	private IGameDetailActivity gameDetailActivity;
	private GameDetailAdapter teamAdapter;
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

		List<List<GameRole>> allTeamRoleLists = new ArrayList<>();

		for(GameTeam team : game.getTeams()){
			allTeamRoleLists.add(team.getRoles());
		}

		teamAdapter = new GameDetailAdapter(game.getTeams(), allTeamRoleLists);
		teamRecyclerView.setAdapter(teamAdapter);
	}
}
