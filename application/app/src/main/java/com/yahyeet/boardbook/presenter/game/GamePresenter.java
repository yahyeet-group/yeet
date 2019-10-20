package com.yahyeet.boardbook.presenter.game;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;

import com.yahyeet.boardbook.activity.home.game.IGameFragment;
import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.model.handler.GameHandler;
import com.yahyeet.boardbook.model.handler.GameHandlerListener;
import com.yahyeet.boardbook.presenter.BoardbookSingleton;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GamePresenter implements GameHandlerListener {

	// TODO: Help me from this UI thread hell
	private Handler uiHandler;

	private IGameFragment gameFragment;
	private BaseAdapter listAdapter;
	private BaseAdapter gridAdapter;

	private boolean gameListEnabled;
	private boolean gameGridEnabled;

	final private List<Game> gameDatabase = new ArrayList<>();
	private List<Game> all = new ArrayList<>();


	public GamePresenter(IGameFragment gameFragment) {
		this.gameFragment = gameFragment;
		uiHandler = new android.os.Handler(Looper.getMainLooper());
		initiateGamePresenter();

		GameHandler gameHandler = BoardbookSingleton.getInstance().getGameHandler();
		gameHandler.addListener(this);
	}

	public void displayGameList(Context viewContext, ListView gameListView) {
		if (listAdapter == null)
			initiateListAdapter(viewContext, gameListView);
		gameListEnabled = true;
		gameGridEnabled = false;
	}

	public void displayGameGrid(Context viewContext, GridView gameGridView) {
		if (gridAdapter == null)
			initiateGridAdapter(viewContext, gameGridView);
		gameGridEnabled = true;
		gameListEnabled = false;
	}

	private void initiateListAdapter(Context viewContext, ListView gameListView) {
		listAdapter = new GameListAdapter(viewContext, gameDatabase);
		gameListView.setAdapter(listAdapter);

	}

	private void initiateGridAdapter(Context viewContext, GridView gameGridView) {
		gridAdapter = new GameGridAdapter(viewContext, gameDatabase);
		gameGridView.setAdapter(gridAdapter);
	}

	private void initiateGamePresenter() {

		gameFragment.disableViewInteraction();
		BoardbookSingleton.getInstance().getGameHandler().all().thenAccept(initGames -> {
			if (initGames != null) {
				gameDatabase.addAll(initGames);
				all.addAll(initGames);
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
	}

	// TODO: Method requires better name
	public void searchGames(String query) {
		gameDatabase.clear();
		gameDatabase.addAll(findMatchingName(all, query));
		updateAdapters();
	}


	private void updateAdapters() {
		if (gameListEnabled)
			listAdapter.notifyDataSetChanged();
		else if (gameGridEnabled)
			gridAdapter.notifyDataSetChanged();
	}

	// TODO: Write tests for this method
	private List<Game> findMatchingName(List<Game> games, String query) {

		if (query == null)
			return games;

		try {
			return games.stream().filter(game -> game.getName().toLowerCase().contains(query)).collect(Collectors.toList());
		} catch (NullPointerException e) {
			//TODO: Alert that a null name object exists
			return games;
		}
	}

	@Override
	public void onAddGame(Game game) {
		gameDatabase.add(game);
		updateAdapters();
	}

	@Override
	public void onUpdateGame(Game game) {
		for (int i = 0; i < gameDatabase.size(); i++) {
			if (gameDatabase.get(i).getId().equals(game.getId())) {
				gameDatabase.set(i, game);
			}
		}
		updateAdapters();
	}

	@Override
	public void onRemoveGame(Game game) {
		for (int i = 0; i < gameDatabase.size(); i++) {
			if (gameDatabase.get(i).getId().equals(game.getId())) {
				gameDatabase.remove(i);
				break;
			}
		}
		updateAdapters();
	}


}
