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


	public ConfigureTeamPresenter(ConfigureTeamsFragment ctf, CMMasterPresenter cm) {
		this.masterPresenter = cm;
	}
	public void enableGameFeed(RecyclerView playerRecycleView, Context viewContext) {
		this.playerRecycleView = playerRecycleView;
		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(viewContext);
		playerRecycleView.setLayoutManager(layoutManager);
		configureTeamAdapter = new ConfigureTeamAdapter(this);
		playerRecycleView.setAdapter(configureTeamAdapter);


	}

	public CMMasterPresenter getMasterPresenter() {
		return masterPresenter;
	}

	public void finalizeMatch() {
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

		masterPresenter.getCmdh().addPlayer(players);
		masterPresenter.finalizeMatch();

	}

}
