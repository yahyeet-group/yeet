package com.yahyeet.boardbook.presenter;

import com.yahyeet.boardbook.activity.IGameDetailActivity;
import com.yahyeet.boardbook.model.entity.Game;

import java.util.concurrent.ExecutionException;

public class GameDetailPresenter {

    private IGameDetailActivity gameDetailActivity;
    private Game game;

    public GameDetailPresenter(IGameDetailActivity gameDetailActivity, String gameID){
        this.gameDetailActivity = gameDetailActivity;
        try {
            game = BoardbookSingleton.getInstance().getGameHandler().find(gameID).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void initiateGameDetail(){
        gameDetailActivity.setGameName(game.getName());
        gameDetailActivity.setGameDescription(game.getDescription());
        gameDetailActivity.setGameRules(game.getRules());
    }
}
