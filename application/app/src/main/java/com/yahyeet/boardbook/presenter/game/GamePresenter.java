package com.yahyeet.boardbook.presenter.game;

import android.content.Context;
import android.os.Looper;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.activity.IFutureInteractable;
import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.model.handler.GameHandler;
import com.yahyeet.boardbook.model.handler.IGameHandlerListener;
import com.yahyeet.boardbook.presenter.AbstractSearchAdapter;
import com.yahyeet.boardbook.presenter.FindAllPresenter;
import com.yahyeet.boardbook.presenter.BoardbookSingleton;

/**
 * Presenter for the game activity
 */
public class GamePresenter extends FindAllPresenter<Game, GameHandler> implements IGameHandlerListener {

	private IFutureInteractable gameFragment;
	private RecyclerView.LayoutManager listLayoutManager;
	private RecyclerView.LayoutManager gridLayoutManager;

	private AbstractSearchAdapter<Game> searchAdapter;
	private DisplayType currentDisplayType;

	public GamePresenter(IFutureInteractable gameFragment, Context viewContext) {
		super(gameFragment);
		this.gameFragment = gameFragment;
		currentDisplayType = DisplayType.LIST;


		GameHandler gameHandler = BoardbookSingleton.getInstance().getGameHandler();

		fillDatabase(gameHandler, GameHandler.generatePopulatorConfig(false, true));
		gameHandler.addListener(this);



		setLayoutManagers(viewContext);
		searchAdapter = new GameAdapter(getDatabase(), viewContext, DisplayType.LIST);
		setAdapter(searchAdapter);

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
		searchAdapter.getFilter().filter(query);
	}


	private void updateGameAdapter(){
		((GameAdapter) getAdapter()).setDisplayType(currentDisplayType);
	}

	private void notifyAdapter() {
		new android.os.Handler(Looper.getMainLooper())
			.post(() -> getAdapter().notifyDataSetChanged());

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
	public void onRemoveGame(String id) {
		for (int i = 0; i < getDatabase().size(); i++) {
			if (getDatabase().get(i).getId().equals(id)) {
				getDatabase().remove(i);
				break;
			}
		}
		notifyAdapter();
	}


}
