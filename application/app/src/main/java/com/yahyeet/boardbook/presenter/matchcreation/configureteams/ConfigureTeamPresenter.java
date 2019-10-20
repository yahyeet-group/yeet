package com.yahyeet.boardbook.presenter.matchcreation.configureteams;

import android.content.Context;
import android.widget.Spinner;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.activity.matchcreation.configureteams.ConfigureTeamsFragment;
import com.yahyeet.boardbook.activity.matchcreation.configureteams.IConfigureTeamsFragment;
import com.yahyeet.boardbook.model.entity.MatchPlayer;
import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.presenter.matchcreation.CMMasterPresenter;
import com.yahyeet.boardbook.presenter.matchcreation.selectplayers.PlayerAdapter;

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
		for (int i = 0; i < playerRecycleView.getAdapter().getItemCount(); i++) {

			ConfigureTeamAdapter.PlayerViewHolder holder = (ConfigureTeamAdapter.PlayerViewHolder) playerRecycleView.findViewHolderForAdapterPosition(i);
			Spinner teamSpinner = holder.getSpinners()[0];
			Spinner roleSpinner = holder.getSpinners()[1];

			
		}
	}

}
