package com.yahyeet.boardbook.presenter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;

import com.yahyeet.boardbook.activity.IGameFragment;
import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.presenter.adapter.GameGridAdapter;
import com.yahyeet.boardbook.presenter.adapter.GameListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

public class GamePresenter {

    private IGameFragment gameFragment;
    private List<Game> dataset;
    private BaseAdapter listAdapter;
    private BaseAdapter gridAdapter;

    // Initiated to no initial search
    private String lastQuery = "";

    private boolean gameListEnabled;
    private boolean gameGridEnabled;



    public GamePresenter(IGameFragment gameFragment) {
        this.gameFragment = gameFragment;
    }

    public void displayGameList(Context viewContext, ListView gameListView){
        if(listAdapter == null)
            enableGameList(viewContext, gameListView);
        gameListEnabled = true;
        gameGridEnabled = false;

        updateDataset(lastQuery);
    }

    public void displayGameGrid(Context viewContext, GridView gameGridView){
        if(gridAdapter == null)
            enableGameGrid(viewContext, gameGridView);
        gameGridEnabled = true;
        gameListEnabled = false;

        updateDataset(lastQuery);
    }

    private void enableGameList(Context viewContext, ListView gameListView){
        if(dataset == null)
            initiateDataset();

        listAdapter = new GameListAdapter(viewContext, dataset);
        gameListView.setAdapter(listAdapter);

    }

    private void enableGameGrid(Context viewContext, GridView gameGridView){
        // TODO: Move shared code out to separate method, setup method?
        if(dataset == null)
            initiateDataset();

        gridAdapter = new GameGridAdapter(viewContext, dataset);
        gameGridView.setAdapter(gridAdapter);
    }



    public void updateGamesWithQuery(String query){
        lastQuery = query;
        updateDataset(query);
        if(gameListEnabled)
            listAdapter.notifyDataSetChanged();
        else if(gameGridEnabled)
            gridAdapter.notifyDataSetChanged();

    }

    private void initiateDataset(){
        dataset = new ArrayList<>();

        // TODO: Long names mess with other objects in grid
        // TODO: Enable when boardbook has games
        BoardbookSingleton.getInstance().getGameHandler().all().thenApply((Function<List<Game>, Object>) dataset::addAll);

        // Cashes all games, remove later
        //mockGames.addAll(dataset);
    }

    // TODO: Method requires better name
    private void updateDataset(String query){
        // Test Code

        List<Game> games = new ArrayList<>();
        try{
            // TODO: Needs to be implemented, might cause lag in app
            games = BoardbookSingleton.getInstance().getGameHandler().all().get();

        }
        catch (InterruptedException | ExecutionException e){
            // TODO: Dunno what to do here, others required
        }

        dataset.clear();

        if(games != null && games.size() > 0){
            games = findMatchingName(games, query);

            // Need to keep same object in listAdapter and this class
            dataset.addAll(games);
        }
    }

    // TODO: Write tests for this method
    private List<Game> findMatchingName(List<Game> games, String query){
        List<Game> matchingGames = new ArrayList<>();
        if(TextUtils.isEmpty(query))
            return games;

        for(Game g : games){
            if(g.getName().toLowerCase().contains(query.toLowerCase())){
                matchingGames.add(g);
            }
        }
        return matchingGames;
    }
}
