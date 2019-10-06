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

public class GamePresenter {

    private IGameFragment gameFragment;
    private List<Game> dataset;
    private BaseAdapter listAdapter;
    private BaseAdapter gridAdapter;

    private String lastQuery;

    private boolean gameListEnabled;
    private boolean gameGridEnabled;

    List<Game> mockGames = new ArrayList<>();



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

        // TODO: Enable when boardbook has games
        //BoardbookSingleton.getInstance().getGameHandler().all().thenApply((Function<List<Game>, Object>) dataset::addAll);


        // TODO: Long names mess with other objects in grid
        dataset.add(new Game("First Name", "Desc", "Null"));
        dataset.add(new Game("Second Name", "Desc", "Null"));
        dataset.add(new Game("Third Name ", "Desc", "Null"));
        dataset.add(new Game("Fourth name", "Desc", "Null"));

        for(int i = 0; i < 20; i++){
            dataset.add(new Game( "Name: " + i, "Desc", "Null"));
        }

        // Cashes all games, remove later
        mockGames.addAll(dataset);
    }

    // TODO: Method requires better name
    private void updateDataset(String query){
        // Test Code
        List<Game> games = findMatchingName(mockGames, query);

        //List<Game> games = new ArrayList<>();
        /*try{
            // TODO: Needs to be implemented, might cause lag in app
            games = findMatchingName(BoardbookSingleton.getInstance().getGameHandler().all().get(), query);
        }
        catch (InterruptedException | ExecutionException e){
            // TODO: Dunno what to do here, others required
        }*/

        // Need to keep same object listAdapter and this class
        dataset.clear();
        dataset.addAll(games);
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
