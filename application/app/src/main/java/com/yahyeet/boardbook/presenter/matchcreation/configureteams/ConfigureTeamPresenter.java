package com.yahyeet.boardbook.presenter.matchcreation.configureteams;

import android.content.Context;
import android.widget.Spinner;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.activity.matchcreation.configureteams.ConfigureTeamsFragment;
import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.model.entity.GameRole;
import com.yahyeet.boardbook.model.entity.GameTeam;
import com.yahyeet.boardbook.model.entity.MatchPlayer;
import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.presenter.matchcreation.CMMasterPresenter;

import java.util.ArrayList;
import java.util.List;

public class ConfigureTeamPresenter {

	private ConfigureTeamAdapter configureTeamAdapter;

	private RecyclerView playerRecycleView;
	private CMMasterPresenter masterPresenter;

	/**
	 * @Author Nox/Aaron Sandgren
	 * This is the Presenter for the ConfigureTeams fragment.
	 * This class binds references to the MasterPresenter, Enables the RecycleView and gives it the correct adapter
	 */
	public ConfigureTeamPresenter(ConfigureTeamsFragment ctf, CMMasterPresenter cm) {
		this.masterPresenter = cm;
	}

	public void repopulateMatches() {
		configureTeamAdapter.notifyDataSetChanged();
	}

	public void enableGameFeed(RecyclerView playerRecycleView, Context viewContext) {
		this.playerRecycleView = playerRecycleView;
		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(viewContext);
		playerRecycleView.setLayoutManager(layoutManager);
		//TODO: Replace with matches from user
		User[] testSet = {new User(), new User()};
		configureTeamAdapter = new ConfigureTeamAdapter(this);
		playerRecycleView.setAdapter(configureTeamAdapter);


	}

	public CMMasterPresenter getMasterPresenter() {
		return masterPresenter;
	}

	/**
	 * This finalizes the match in that it takes all the players selected together with the teams selected
	 * and creates MatchPlayer objects that correspond with the user selected configuration
	 */
	// This method is a bit messy but we didn't want the view to be dependent on the Enteties and thefore couldnt
	// Create the MatchPlayer in the adapter which would have been cleaner. Here all the data is gotten from the adapter
	// And then bound to a correct MatchPlayer
	public void finalizeMatch() {
		// Variables so that I don't have to initialize in the loop.
		List<MatchPlayer> players = new ArrayList<>();
		Spinner teamSpinner;
		Spinner roleSpinner;
		Game game = getMasterPresenter().getCmdh().getGame();
		List<GameTeam> teams = game.getTeams();
		GameTeam team = null;
		GameRole role = null;
		List<User> selectedPlayers = getMasterPresenter().getCmdh().getSelectedPlayers();
		User user;

		for (int i = 0; i < playerRecycleView.getAdapter().getItemCount(); i++) {
			// reset variables after every loop
			role = null;
			team = null;

			// Getting spinners and from every entry in the RecycleView
			ConfigureTeamAdapter.PlayerViewHolder holder = (ConfigureTeamAdapter.PlayerViewHolder) playerRecycleView.findViewHolderForAdapterPosition(i);
			teamSpinner = holder.getSpinners()[0];
			roleSpinner = holder.getSpinners()[1];

			user = selectedPlayers.get(i);

			if (teamSpinner.getSelectedItemPosition() != 0) {
				team = teams.get(teamSpinner.getSelectedItemPosition() - 1);

				if (roleSpinner.getSelectedItemPosition() != 0) {
					role = teams.get(teamSpinner.getSelectedItemPosition() - 1).getRoles().get(roleSpinner.getSelectedItemPosition() -1);
				}
			}

			players.add(new MatchPlayer(user, role, team, holder.getWin()));

		}
		// Add the match players into the DataObject and call finalize on MasterPresenter
		masterPresenter.getCmdh().addPlayer(players);
		masterPresenter.finalizeMatch();

	}

}
