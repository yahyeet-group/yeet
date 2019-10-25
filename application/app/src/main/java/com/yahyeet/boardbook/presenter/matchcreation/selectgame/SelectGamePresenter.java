package com.yahyeet.boardbook.presenter.matchcreation.selectgame;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.activity.IFutureInteractable;
import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.model.handler.GameHandler;
import com.yahyeet.boardbook.presenter.FindAllPresenter;
import com.yahyeet.boardbook.presenter.BoardbookSingleton;
import com.yahyeet.boardbook.activity.matchcreation.selectgame.ISelectGameFragment;
import com.yahyeet.boardbook.presenter.matchcreation.CMMasterPresenter;

/**
 * This is the Presenter for the SelectGame fragment.
 * This class binds references to the MasterPresenter, Enables the RecycleView and gives it the correct adapter
 */
public class SelectGamePresenter extends FindAllPresenter<Game, GameHandler> {

	private ISelectGameFragment selectGameFragment;
	private CMMasterPresenter masterPresenter;


	public SelectGamePresenter(ISelectGameFragment selectGameFragment, CMMasterPresenter cm) {
		super((IFutureInteractable) selectGameFragment);
		this.selectGameFragment = selectGameFragment;
		this.masterPresenter = cm;

		fillDatabase(BoardbookSingleton.getInstance().getGameHandler(),
			null);
		setAdapter(new GamesAdapter(getDatabase(), this));

	}

	public void enableGameFeed(RecyclerView gameRecycleView, Context viewContext) {
		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(viewContext);
		gameRecycleView.setLayoutManager(layoutManager);
		gameRecycleView.setAdapter(getAdapter());
	}

	public CMMasterPresenter getMasterPresenter(){
		return masterPresenter;
	}

}
