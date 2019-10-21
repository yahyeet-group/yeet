package com.yahyeet.boardbook.presenter.matchcreation.selectgame;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.activity.IFutureInteractable;
import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.model.handler.GameHandler;
import com.yahyeet.boardbook.presenter.AdapterPresenter;
import com.yahyeet.boardbook.presenter.matchcreation.CMMasterPresenter;
import com.yahyeet.boardbook.activity.matchcreation.selectgame.ISelectGameFragment;


public class SelectGamePresenter extends AdapterPresenter<Game, GameHandler> {


	private ISelectGameFragment selectGameFragment;
	private CMMasterPresenter masterPresenter;


	public SelectGamePresenter(ISelectGameFragment selectGameFragment, CMMasterPresenter cm) {
		super((IFutureInteractable) selectGameFragment);
		this.selectGameFragment = selectGameFragment;
		this.masterPresenter = cm;


		setAdapter(new GamesAdapter(getDatabase(), this));

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


		gameRecycleView.setAdapter(getAdapter());
	}

	public CMMasterPresenter getMasterPresenter(){
		return masterPresenter;
	}

}
