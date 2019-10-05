package com.yahyeet.boardbook.presenter;

import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.yahyeet.boardbook.activity.IGameFragment;
import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.presenter.adapter.GameListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class GameListPresenter {

    private IGameFragment gameFragment;
    private BaseAdapter adapter;
    private List<Game> dataset;
    List<Game> mockGames = new ArrayList<>();


    public GameListPresenter(IGameFragment gameFragment) {
        this.gameFragment = gameFragment;
    }


    public void enableGameList(Context viewContext, ListView gameListView){

        dataset = new ArrayList<>();

        // TODO: Enable when boardbook has games
        //BoardbookSingleton.getInstance().getGameHandler().all().thenApply((Function<List<Game>, Object>) dataset::addAll);



        dataset.add(new Game("First Name", "Desc", "Null"));
        dataset.add(new Game("Second Name", "Desc", "Null"));

        // Cashes all games, remove later
        mockGames.addAll(dataset);

        adapter = new GameListAdapter(viewContext, dataset);
        gameListView.setAdapter(adapter);

    }

    public void updateGamesWithQuery(String query){
        updateDataset(query);

        if(adapter.areAllItemsEnabled())
            System.out.println("Items enabled");

        adapter.notifyDataSetChanged();
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

        // Need to keep same object adapter and this class
        dataset.clear();
        dataset.addAll(games);
    }

    // TODO: Write tests for this method
    private List<Game> findMatchingName(List<Game> games, String query){
        List<Game> matchingGames = new ArrayList<>();
        if(query.isEmpty())
            return games;

        for(Game g : games){
            if(g.getName().toLowerCase().contains(query.toLowerCase())){
                matchingGames.add(g);
            }
        }
        return matchingGames;
    }
}
