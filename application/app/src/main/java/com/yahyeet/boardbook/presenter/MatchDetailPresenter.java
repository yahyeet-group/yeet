package com.yahyeet.boardbook.presenter;

import android.content.Context;
import android.content.res.Resources;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.activity.homeActivity.matchfeedFragment.matchDetailActivity.IMatchDetailActivity;
import com.yahyeet.boardbook.activity.homeActivity.matchfeedFragment.matchDetailActivity.MatchPlayerAdapter;
import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.model.entity.GameRole;
import com.yahyeet.boardbook.model.entity.GameTeam;
import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.model.entity.MatchPlayer;
import com.yahyeet.boardbook.model.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MatchDetailPresenter {

	private IMatchDetailActivity matchDetailActivity;
	private MatchPlayerAdapter matchPlayerAdapter;
	private Match match;

	public MatchDetailPresenter(IMatchDetailActivity matchDetailActivity, String matchID) {
		this.matchDetailActivity = matchDetailActivity;
		/*try {
			match = BoardbookSingleton.getInstance().getMatchHandler().find(matchID).get();
		} catch (ExecutionException | InterruptedException e) {
			// TODO: What to do here?
		}*/

		List<MatchPlayer> playerList = new ArrayList<>();
		playerList.add(new MatchPlayer("", new User("Vex"), new GameRole("Morganna"), new GameTeam("Evil"), true));
		playerList.add(new MatchPlayer("", new User("Nox"), new GameRole("Merlin"), new GameTeam("Good"), false));
		playerList.add(new MatchPlayer("", new User("Håll"), null, new GameTeam("Good"), false));
		playerList.add(new MatchPlayer("", new User("Rosen"), new GameRole("Servant of Merlin"), null, false));
		Game game = new Game("Avalonian", "", 0, 0, 0);

		match = new Match("", playerList, game);

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
	 * @param matchplayerRecyclerView view that will receive new adapter
	 * @param viewContext structure of current view
	 */
	public void enableMatchplayerAdapter(RecyclerView matchplayerRecyclerView, Context viewContext, Resources resources) {

		RecyclerView.LayoutManager layoutManager = new GridLayoutManager(viewContext, 2);

		matchplayerRecyclerView.setLayoutManager(layoutManager);

		matchPlayerAdapter = new MatchPlayerAdapter(match.getPlayers(), resources);
		matchplayerRecyclerView.setAdapter(matchPlayerAdapter);
	}
	
}
