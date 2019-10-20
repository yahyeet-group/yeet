package com.yahyeet.boardbook.presenter.game;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.activity.home.game.IGameFragment;
import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.model.handler.GameHandler;
import com.yahyeet.boardbook.model.handler.GameHandlerListener;
import com.yahyeet.boardbook.presenter.BoardbookSingleton;

import java.util.ArrayList;
import java.util.List;

public class GamePresenter implements GameHandlerListener {

	// TODO: Help me from this UI thread hell
	private Handler uiHandler;

	private IGameFragment gameFragment;
	private GameAdapter listAdapter;
	private GameAdapter gridAdapter;
	private RecyclerView.LayoutManager listLayoutManager;
	private RecyclerView.LayoutManager gridLayoutManager;

	private boolean gameListEnabled;
	private boolean gameGridEnabled;

	final private List<Game> gameDatabase = new ArrayList<>();
	final private List<Game> allGameDatabase = new ArrayList<>();


	public GamePresenter(IGameFragment gameFragment, Context viewContext) {
		this.gameFragment = gameFragment;
		uiHandler = new android.os.Handler(Looper.getMainLooper());

		setLayoutManagers(viewContext);
		initiateAdapters(viewContext);

		initiateGamePresenter();


		GameHandler gameHandler = BoardbookSingleton.getInstance().getGameHandler();
		gameHandler.addListener(this);
	}

	public void displayGameList(RecyclerView recyclerView) {
		recyclerView.setLayoutManager(listLayoutManager);
		recyclerView.setAdapter(listAdapter);

		gameListEnabled = true;
		gameGridEnabled = false;
	}

	public void displayGameGrid(RecyclerView recyclerView) {
		recyclerView.setLayoutManager(gridLayoutManager);
		recyclerView.setAdapter(gridAdapter);

		gameGridEnabled = true;
		gameListEnabled = false;
	}

	private void initiateAdapters(Context viewContext){
		gridAdapter = new GameGridAdapter(gameDatabase, allGameDatabase, viewContext);
		listAdapter = new GameListAdapter(gameDatabase, allGameDatabase, viewContext);
	}

	private void setLayoutManagers(Context viewContext){
		listLayoutManager = new LinearLayoutManager(viewContext);
		gridLayoutManager = new GridLayoutManager(viewContext, 3);
	}

	private void initiateGamePresenter() {

		gameFragment.disableFragmentInteraction();
		BoardbookSingleton.getInstance().getGameHandler().all().thenAccept(initiatedGames -> {
			if (initiatedGames != null) {
				gameDatabase.addAll(initiatedGames);
				allGameDatabase.addAll(initiatedGames);
			}

			uiHandler.post(() -> {
				gameFragment.enableFragmentInteraction();
				updateAdapters();
			});
		}).exceptionally(e -> {
			uiHandler.post(() -> {
				gameFragment.displayLoadingFailed();
				gameFragment.enableFragmentInteraction();
			});
			return null;
		});
	}

	// TODO: Method requires better name
	public void searchGames(String query) {
		if(gameListEnabled)
			listAdapter.getFilter().filter(query);
		else if(gameGridEnabled)
			gridAdapter.getFilter().filter(query);
	}


	private void updateAdapters() {
		if (gameListEnabled)
			listAdapter.notifyDataSetChanged();
		else if (gameGridEnabled)
			gridAdapter.notifyDataSetChanged();
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
