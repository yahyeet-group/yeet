package com.yahyeet.boardbook.presenter;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.activity.CreateingMatches.SelectGame.GamesAdapter;
import com.yahyeet.boardbook.activity.CreateingMatches.SelectGame.ISelectGameFragment;
import com.yahyeet.boardbook.model.entity.Game;

public class CreateMatchPresenter {

    private GamesAdapter gamesAdapter;

    private ISelectGameFragment selectGameFragment;

    public CreateMatchPresenter(ISelectGameFragment selectGameFragment) {
        this.selectGameFragment = selectGameFragment;
    }

    public void repopulateMatches() {
        gamesAdapter.notifyDataSetChanged();
    }

    public void enableGameFeed(RecyclerView gameRecycleView, Context viewContext) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(viewContext);
        gameRecycleView.setLayoutManager(layoutManager);
        //TODO: Replace with matches from user
        Game[] testSet = {new Game("Avalon", "Cool Game that is cool")};
        testSet[0].getName();
        gamesAdapter = new GamesAdapter(testSet);
        gameRecycleView.setAdapter(gamesAdapter);
    }
}
