package com.yahyeet.boardbook.presenter;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.activity.CreateingMatches.CMMasterPresenter;
import com.yahyeet.boardbook.activity.CreateingMatches.SelectGame.GamesAdapter;
import com.yahyeet.boardbook.activity.CreateingMatches.SelectPlayers.PlayerAdapter;
import com.yahyeet.boardbook.activity.CreateingMatches.SelectPlayers.SelectPlayersFragment;
import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.model.entity.User;

import java.util.LinkedList;
import java.util.List;

public class SelectPlayersPresenter {

	private CMMasterPresenter masterPresenter;
	private SelectPlayersFragment spf;
	private PlayerAdapter playerAdapter;

	public SelectPlayersPresenter(SelectPlayersFragment spf, CMMasterPresenter cma) {
		this.masterPresenter = cma;
		this.spf = spf;
	}

	public void repopulateMatches() {
		playerAdapter.notifyDataSetChanged();
	}

	public void enableGameFeed(RecyclerView gameRecycleView, Context viewContext) {
		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(viewContext);
		gameRecycleView.setLayoutManager(layoutManager);
		//TODO: Replace with real users

		List<User> testSet = new LinkedList<>();
		testSet.add(new User());
		playerAdapter = new PlayerAdapter(testSet, this);
		gameRecycleView.setAdapter(playerAdapter);
	}

	public CMMasterPresenter getMasterPresenter() {
		return masterPresenter;
	}

}
