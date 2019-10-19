package com.yahyeet.boardbook.presenter.matchfeed.matchdetail;

import android.content.Context;
import android.content.res.Resources;
import android.os.Looper;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.activity.home.matchfeed.matchdetail.IMatchDetailActivity;
import com.yahyeet.boardbook.model.handler.MatchHandlerListener;
import com.yahyeet.boardbook.presenter.BoardbookSingleton;
import com.yahyeet.boardbook.presenter.matchfeed.matchdetail.MatchPlayerAdapter;
import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.model.entity.GameRole;
import com.yahyeet.boardbook.model.entity.GameTeam;
import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.model.entity.MatchPlayer;
import com.yahyeet.boardbook.model.entity.User;

import java.util.concurrent.ExecutionException;


public class MatchDetailPresenter {

	private IMatchDetailActivity matchDetailActivity;
	private MatchPlayerAdapter matchPlayerAdapter;
	private Match match;

	public MatchDetailPresenter(IMatchDetailActivity matchDetailActivity, String matchID) {
		this.matchDetailActivity = matchDetailActivity;


		Game game = new Game("Avalonian", "A game about Avalon", 1, 4, 8);

		GameTeam team1 = new GameTeam("Evil");
		team1.addRole(new GameRole("Morganna"));
		team1.addRole(new GameRole("Mordred"));

		game.addTeam(team1);

		GameTeam team2 = new GameTeam("Good");
		team2.addRole(new GameRole("Merlin"));
		team2.addRole(new GameRole("Servant of Merlin"));

		game.addTeam(team2);

		BoardbookSingleton.getInstance().getGameHandler().save(game).thenCompose(savedGame -> {
			User liUser = BoardbookSingleton.getInstance().getAuthHandler().getLoggedInUser();

			MatchPlayer player1 = new MatchPlayer(liUser, savedGame.getTeams().get(0).getRoles().get(0), savedGame.getTeams().get(0), true);
			MatchPlayer player2 = new MatchPlayer(liUser, savedGame.getTeams().get(1).getRoles().get(0), savedGame.getTeams().get(1), false);

			Match newMatch = new Match();
			newMatch.setGame(savedGame);
			newMatch.addMatchPlayer(player1);
			newMatch.addMatchPlayer(player2);

			return BoardbookSingleton.getInstance().getMatchHandler().save(newMatch).thenAccept(savedMatch -> {
				BoardbookSingleton.getInstance().getMatchHandler().find(savedGame.getId()).thenAccept(foundMatch -> {
					match = foundMatch;

					new android.os.Handler(Looper.getMainLooper()).post(() -> {
						initiateGameDetail();
						matchDetailActivity.initiateMatchDetailList();
					});

				}).exceptionally(e -> {
					e.printStackTrace();
					return null;
				});

			}).exceptionally(e -> {
				e.printStackTrace();
				return null;
			});
		});


		// TODO: Fix runtime error


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
