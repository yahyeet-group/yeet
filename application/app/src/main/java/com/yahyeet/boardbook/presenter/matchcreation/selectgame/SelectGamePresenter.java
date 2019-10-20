package com.yahyeet.boardbook.presenter.matchcreation.selectgame;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.presenter.matchcreation.CMMasterPresenter;
import com.yahyeet.boardbook.activity.matchcreation.selectgame.ISelectGameFragment;
import com.yahyeet.boardbook.model.entity.Game;

public class SelectGamePresenter {

	private GamesAdapter gamesAdapter;

	private ISelectGameFragment selectGameFragment;

	private CMMasterPresenter masterPresenter;

	public SelectGamePresenter(ISelectGameFragment selectGameFragment, CMMasterPresenter cm) {
		this.selectGameFragment = selectGameFragment;
		this.masterPresenter = cm;

	}

	public void repopulateMatches() {
		gamesAdapter.notifyDataSetChanged();
	}

	public void enableGameFeed(RecyclerView gameRecycleView, Context viewContext) {
		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(viewContext);
		gameRecycleView.setLayoutManager(layoutManager);
		//TODO: Replace with matches from user
		Game[] testSet = {new Game("Avalon", "Cool Game that is cool")};
		testSet[0].getName();
		gamesAdapter = new GamesAdapter(testSet, this);
		gameRecycleView.setAdapter(gamesAdapter);
	}

	public CMMasterPresenter getMasterPresenter(){
		return masterPresenter;
	}
}
