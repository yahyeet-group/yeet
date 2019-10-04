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
import java.util.function.Function;

public class GameListPresenter {

    private IGameFragment gameFragment;


    public GameListPresenter(IGameFragment gameFragment) {
        this.gameFragment = gameFragment;
    }


    public void enableGameList(Context viewContext, ListView gameListView){

        List<Game> gameList = new ArrayList<>();

        // TODO: Enable when boardbook has games
        //BoardbookSingleton.getInstance().getGameHandler().all().thenApply((Function<List<Game>, Object>) gameList::addAll);



        gameList.add(new Game("First Name", "Desc", "Null"));

        BaseAdapter adapter = new GameListAdapter(viewContext, gameList);
        gameListView.setAdapter(adapter);

    }

    public BaseAdapter searchGame(String query, Context context){
        List<Game> games = new ArrayList<>();

        try{
            games = findMatchingName(BoardbookSingleton.getInstance().getGameHandler().all().get(), query);
        }
        catch (InterruptedException | ExecutionException e){
            // TODO: Dunno what to do here, others required
        }

        return new GameListAdapter(context, games);
    }

    // TODO: Write tests for this method
    private List<Game> findMatchingName(List<Game> games, String query){
        List<Game> matchingGames = new ArrayList<>();
        for(Game g : games){
            if(g.getName().contains(query)){
                matchingGames.add(g);
            }
        }
        return matchingGames;
    }
}
