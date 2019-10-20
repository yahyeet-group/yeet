package com.yahyeet.boardbook.presenter.game;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Adapter;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.activity.home.game.IGameFragment;
import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.model.handler.GameHandler;
import com.yahyeet.boardbook.model.handler.GameHandlerListener;
import com.yahyeet.boardbook.presenter.AdapterPresenter;
import com.yahyeet.boardbook.presenter.BoardbookSingleton;

import java.util.ArrayList;
import java.util.List;

public class GamePresenter extends AdapterPresenter<Game, GameHandler> implements GameHandlerListener {


	private IGameFragment gameFragment;
	private RecyclerView.LayoutManager listLayoutManager;
	private RecyclerView.LayoutManager gridLayoutManager;

	private DisplayType currentDisplayType;
	private GameAdapter listAdapter;
	private GameAdapter gridAdapter;

	public GamePresenter(IGameFragment gameFragment, Context viewContext) {
		super(gameFragment);
		this.gameFragment = gameFragment;
		currentDisplayType = DisplayType.LIST;


		GameHandler gameHandler = BoardbookSingleton.getInstance().getGameHandler();

		pullAllDataToDatabase(gameHandler);
		gameHandler.addListener(this);

		listAdapter = new GameAdapter(getDatabase(), viewContext, DisplayType.LIST);
		gridAdapter = new GameAdapter(getDatabase(), viewContext, DisplayType.GRID);

		setLayoutManagers(viewContext);
		setAdapter(listAdapter);


	}

	public void displayGameList(RecyclerView recyclerView) {
		recyclerView.setLayoutManager(listLayoutManager);

		currentDisplayType = DisplayType.LIST;
		updateAdapters();

		recyclerView.setAdapter(getAdapter());
	}

	public void displayGameGrid(RecyclerView recyclerView) {
		recyclerView.setLayoutManager(gridLayoutManager);

		currentDisplayType = DisplayType.GRID;
		updateAdapters();

		recyclerView.setAdapter(getAdapter());
	}

	private void setLayoutManagers(Context viewContext){
		listLayoutManager = new LinearLayoutManager(viewContext);
		gridLayoutManager = new GridLayoutManager(viewContext, 3);
	}

	/*private void initiateGamePresenter() {

		gameFragment.disableViewInteraction();
		BoardbookSingleton.getInstance().getGameHandler().all().thenAccept(initiatedGames -> {
			if (initiatedGames != null) {
				gameDatabase.addAll(initiatedGames);
			}

			uiHandler.post(() -> {
				gameFragment.enableViewInteraction();
				updateAdapters();
			});
		}).exceptionally(e -> {
			uiHandler.post(() -> {
				gameFragment.displayLoadingFailed();
				gameFragment.enableViewInteraction();
			});
			return null;
		});
	}*/

	// TODO: Method requires better name
	public void searchGames(String query) {
		getAdapter().getFilter().filter(query);
	}


	private void updateAdapters() {
		if(currentDisplayType == DisplayType.LIST)
			setAdapter(listAdapter);
		else
			setAdapter(gridAdapter);

		getAdapter().notifyDataSetChanged();
	}

	@Override
	public void onAddGame(Game game) {
		getDatabase().add(game);
		updateAdapters();
	}

	@Override
	public void onUpdateGame(Game game) {
		for (int i = 0; i < getDatabase().size(); i++) {
			if (getDatabase().get(i).getId().equals(game.getId())) {
				getDatabase().set(i, game);
			}
		}
		updateAdapters();
	}

	@Override
	public void onRemoveGame(Game game) {
		for (int i = 0; i < getDatabase().size(); i++) {
			if (getDatabase().get(i).getId().equals(game.getId())) {
				getDatabase().remove(i);
				break;
			}
		}
		updateAdapters();
	}


}
