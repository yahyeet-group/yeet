package com.yahyeet.boardbook.presenter.matchcreation.configureteams;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.activity.matchcreation.configureteams.ConfigureTeamsFragment;
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

	public void finalizeMatch() {
		List<MatchPlayer> players = new ArrayList<>();
		for (int i = 0; i < playerRecycleView.getAdapter().getItemCount(); i++) {

			ConfigureTeamAdapter.PlayerViewHolder holder = (ConfigureTeamAdapter.PlayerViewHolder) playerRecycleView.findViewHolderForAdapterPosition(i);
			players.add(holder.getMatchPlayer());
		}

		masterPresenter.getCmdh().addPlayer(players);
		masterPresenter.finalizeMatch();

	}

}
