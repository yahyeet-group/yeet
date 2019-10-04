package com.yahyeet.boardbook.presenter;

import android.content.Context;
import android.widget.BaseAdapter;

import com.yahyeet.boardbook.activity.IGameFragment;
import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.presenter.adapter.GameListAdapter;

import java.util.ArrayList;
import java.util.List;

public class GameListPresenter {

    private IGameFragment gameFragment;


    public GameListPresenter(IGameFragment gameFragment) {
        this.gameFragment = gameFragment;
    }

    public BaseAdapter searchGame(String query, Context context){
        final List<Game> games = new ArrayList<>();
        BoardbookSingleton.getInstance().getGameHandler().all().thenAccept(allGames -> {
            games.addAll(findMatchingName(allGames, query));

        });
        //TODO: Needs to wait on thenAccept?
        return new GameListAdapter(context, games);
    }

    // TODO: Write tests for this method
    private List<Game> findMatchingName(List<Game> allGames, String query){
        List<Game> matchingGames = new ArrayList<>();
        for(Game g : allGames){
            if(g.getName().contains(query)){
                matchingGames.add(g);
            }
        }
        return matchingGames;
    }
}
