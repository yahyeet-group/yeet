package com.yahyeet.boardbook.presenter.game;

import android.content.Context;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.activity.IFutureInteractable;
import com.yahyeet.boardbook.activity.home.game.IGameFragment;
import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.model.handler.GameHandler;
import com.yahyeet.boardbook.model.handler.GameHandlerListener;
import com.yahyeet.boardbook.presenter.AdapterPresenter;
import com.yahyeet.boardbook.presenter.BoardbookSingleton;

public class GamePresenter extends AdapterPresenter<Game, GameHandler> implements GameHandlerListener {


	private IFutureInteractable gameFragment;
	private RecyclerView.LayoutManager listLayoutManager;
	private RecyclerView.LayoutManager gridLayoutManager;

	private DisplayType currentDisplayType;

	public GamePresenter(IFutureInteractable gameFragment, Context viewContext) {
		super(gameFragment);
		this.gameFragment = gameFragment;
		currentDisplayType = DisplayType.LIST;


		GameHandler gameHandler = BoardbookSingleton.getInstance().getGameHandler();

		fillDatabase(gameHandler);
		gameHandler.addListener(this);



		setLayoutManagers(viewContext);
		setAdapter(new GameAdapter(getDatabase(), viewContext, DisplayType.LIST));

	}

	public void bindAdapterToView(RecyclerView recyclerView){
		recyclerView.setLayoutManager(listLayoutManager);
		recyclerView.setAdapter(getAdapter());
	}

	public void displayGameList(RecyclerView recyclerView) {
		currentDisplayType = DisplayType.LIST;

		recyclerView.setLayoutManager(listLayoutManager);
		updateGameAdapter();
		notifyAdapter();
	}

	public void displayGameGrid(RecyclerView recyclerView) {
		currentDisplayType = DisplayType.GRID;

		recyclerView.setLayoutManager(gridLayoutManager);
		updateGameAdapter();
		notifyAdapter();
	}

	private void setLayoutManagers(Context viewContext){
		listLayoutManager = new LinearLayoutManager(viewContext);
		gridLayoutManager = new GridLayoutManager(viewContext, 3);
	}

	public void searchGames(String query) {
		getAdapter().getFilter().filter(query);
	}


	private void updateGameAdapter(){
		((GameAdapter) getAdapter()).setDisplayType(currentDisplayType);
	}

	private void notifyAdapter() {
		getAdapter().notifyDataSetChanged();
	}

	@Override
	public void onAddGame(Game game) {
		getDatabase().add(game);
		notifyAdapter();
	}

	@Override
	public void onUpdateGame(Game game) {
		for (int i = 0; i < getDatabase().size(); i++) {
			if (getDatabase().get(i).getId().equals(game.getId())) {
				getDatabase().set(i, game);
			}
		}
		notifyAdapter();
	}

	@Override
	public void onRemoveGame(Game game) {
		for (int i = 0; i < getDatabase().size(); i++) {
			if (getDatabase().get(i).getId().equals(game.getId())) {
				getDatabase().remove(i);
				break;
			}
		}
		notifyAdapter();
	}


}
