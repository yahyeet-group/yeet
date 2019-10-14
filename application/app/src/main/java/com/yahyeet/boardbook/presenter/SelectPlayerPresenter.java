package com.yahyeet.boardbook.presenter;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.activity.CreateingMatches.SelectGame.GamesAdapter;
import com.yahyeet.boardbook.activity.CreateingMatches.SelectGame.ISelectGameFragment;
import com.yahyeet.boardbook.activity.CreateingMatches.SelectPlayers.ISelectPlayersFragment;
import com.yahyeet.boardbook.activity.CreateingMatches.SelectPlayers.PlayerAdapter;
import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.model.entity.User;

public class SelectPlayerPresenter {

    private PlayerAdapter playerAdapter;

    private ISelectGameFragment selectGameFragment;

    private ISelectPlayersFragment spf;

    public SelectPlayerPresenter(ISelectPlayersFragment spf){

        this.spf = spf;
    }

    public void enableGameFeed(RecyclerView playerRecycleView, Context viewContext) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(viewContext);
        playerRecycleView.setLayoutManager(layoutManager);
        //TODO: Replace with matches from user
        User[] testSet = {new User(), new User()};
        playerAdapter = new PlayerAdapter(testSet);
        playerRecycleView.setAdapter(playerAdapter);
    }
}
