package com.yahyeet.boardbook.presenter.matchcreation.selectgame;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.presenter.BoardbookSingleton;
import com.yahyeet.boardbook.presenter.matchcreation.CMMasterPresenter;
import com.yahyeet.boardbook.activity.matchcreation.selectgame.ISelectGameFragment;

import java.util.ArrayList;
import java.util.List;

public class SelectGamePresenter  {

	private GamesAdapter gamesAdapter;
	private List<Game> dataset;

	private ISelectGameFragment selectGameFragment;
	private CMMasterPresenter masterPresenter;


	public SelectGamePresenter(ISelectGameFragment selectGameFragment, CMMasterPresenter cm) {

		this.selectGameFragment = selectGameFragment;
		this.masterPresenter = cm;


	}

	public void updateAdapter() {
		gamesAdapter.notifyDataSetChanged();
	}

	public void enableGameFeed(RecyclerView gameRecycleView, Context viewContext) {
		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(viewContext);
		gameRecycleView.setLayoutManager(layoutManager);
		/*Game testGame = new Game("Avalon", "Cool Game", 3, 5, 10);
		GameTeam mom = new GameTeam();
		mom.setName("Minions of Mordred");
		GameRole mordred = new GameRole();
		mordred.setName("Mordred");
		GameRole minion = new GameRole();
		minion.setName("Minion of Mordred");
		mom.addRole(mordred);
		mom.addRole(minion);
		testGame.addTeam(mom);
		GameTeam som = new GameTeam();
		som.setName("Servants of Merlin");
		GameRole merlin = new GameRole();
		merlin.setName("Merlin");
		GameRole servant = new GameRole();
		servant.setName("Servant of Merlin");
		som.addRole(merlin);
		som.addRole(servant);
		testGame.addTeam(som);


		List<Game> testSet = new ArrayList<>();
		testSet.add(testGame);*/



		gamesAdapter = new GamesAdapter(dataset, this);
		gameRecycleView.setAdapter(gamesAdapter);
	}

	public CMMasterPresenter getMasterPresenter(){
		return masterPresenter;
	}

}
