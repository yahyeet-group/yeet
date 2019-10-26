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
import com.yahyeet.boardbook.presenter.FindOnePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Presenter for the game detail activity
 */
public class GameDetailPresenter extends FindOnePresenter<Game, GameHandler> {


	private IGameDetailActivity gameDetailActivity;
	private GameDetailAdapter teamAdapter;

	private List<GameTeam> teams = new ArrayList<>();
	private List<List<GameRole>> roleLists = new ArrayList<>();

	public GameDetailPresenter(IGameDetailActivity gameDetailActivity, String gameID) {
		super((IFutureInteractable) gameDetailActivity);
		this.gameDetailActivity = gameDetailActivity;

		findEntity(BoardbookSingleton.getInstance().getGameHandler(), gameID,
			GameHandler.generatePopulatorConfig(false, true));

	}

	public void initiateGameDetail(Game game) {
		gameDetailActivity.setGameName(game.getName());
		gameDetailActivity.setGameDescription(game.getDescription());
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
		initiateGameDetail(entity);

		teams.clear();
		teams.addAll(entity.getTeams());

		roleLists.clear();
		getEntity().getTeams().forEach(team -> {
			roleLists.add(team.getRoles());
		});

		gameDetailActivity.enableRecyclerView();

	}
}
