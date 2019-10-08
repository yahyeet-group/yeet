package com.yahyeet.boardbook.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;

import com.yahyeet.boardbook.activity.IGameFragment;
import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.model.handler.GameHandler;
import com.yahyeet.boardbook.model.handler.GameHandlerListener;
import com.yahyeet.boardbook.presenter.adapter.GameGridAdapter;
import com.yahyeet.boardbook.presenter.adapter.GameListAdapter;

import java.util.ArrayList;
import java.util.List;

public class GamePresenter implements GameHandlerListener {

    // TODO: Help me from this UI thread hell
    private Handler uiHandler;

    private IGameFragment gameFragment;
    private BaseAdapter listAdapter;
    private BaseAdapter gridAdapter;

    private boolean gameListEnabled;
    private boolean gameGridEnabled;

    final private List<Game> gameDatabase = new ArrayList<>();

    public GamePresenter(IGameFragment gameFragment) {
        this.gameFragment = gameFragment;
        uiHandler = new android.os.Handler(Looper.getMainLooper());
        initiateGamePresenter();

        GameHandler gameHandler = BoardbookSingleton.getInstance().getGameHandler();
        gameHandler.addListener(this);
    }

    public void displayGameList(Context viewContext, ListView gameListView) {
        if (listAdapter == null)
            enableGameList(viewContext, gameListView);
        gameListEnabled = true;
        gameGridEnabled = false;
    }

    public void displayGameGrid(Context viewContext, GridView gameGridView) {
        if (gridAdapter == null)
            enableGameGrid(viewContext, gameGridView);
        gameGridEnabled = true;
        gameListEnabled = false;
    }

    private void enableGameList(Context viewContext, ListView gameListView) {
        listAdapter = new GameListAdapter(viewContext, gameDatabase);
        gameListView.setAdapter(listAdapter);

    }

    private void enableGameGrid(Context viewContext, GridView gameGridView) {
        gridAdapter = new GameGridAdapter(viewContext, gameDatabase);
        gameGridView.setAdapter(gridAdapter);
    }

    private void initiateGamePresenter() {

        gameFragment.disableFragmentInteraction();
        BoardbookSingleton.getInstance().getGameHandler().all().thenAccept(initGames -> {
            gameDatabase.addAll(initGames);
            uiHandler.post(() -> {
                gameFragment.enableFragmentInteraction();
                updateAdapters();
            });
        });
    }

    // TODO: Method requires better name
    public void updateGamesWithQuery(String query) {
        gameFragment.disableFragmentInteraction();
        BoardbookSingleton.getInstance().getGameHandler().all().thenAccept(games -> {
            gameDatabase.clear();
            if (games != null && games.size() > 0) {
                games = findMatchingName(games, query);

                // Need to keep same object in listAdapter and this class
                gameDatabase.addAll(games);
            }

            uiHandler.post(() -> {
                gameFragment.enableFragmentInteraction();
                updateAdapters();
            });

        });
        /*try {


        } catch (Exception e) {
            // TODO: Dunno what to do here, others required
        }*/


    }

    @Override
    public void onAddGame(Game game) {
        updateAdapters();
    }

    @Override
    public void onUpdateGame(Game game) {
        updateAdapters();
    }

    @Override
    public void onRemoveGame(Game game) {
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
        List<Game> matchingGames = new ArrayList<>();
        if (TextUtils.isEmpty(query))
            return games;

        for (Game g : games) {
            if(g.getName() == null){
                return matchingGames;
            }

            if (g.getName().toLowerCase().contains(query.toLowerCase())) {
                matchingGames.add(g);
            }
        }
        return matchingGames;
    }


}
