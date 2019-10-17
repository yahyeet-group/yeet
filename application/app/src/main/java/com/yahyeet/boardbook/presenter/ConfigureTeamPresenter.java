package com.yahyeet.boardbook.presenter;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.activity.CreateingMatches.CMMasterPresenter;
import com.yahyeet.boardbook.activity.CreateingMatches.SelectGame.ISelectGameFragment;
import com.yahyeet.boardbook.activity.CreateingMatches.ConfigureTeams.IConfigureTeamsFragment;
import com.yahyeet.boardbook.activity.CreateingMatches.ConfigureTeams.ConfigureTeamAdapter;
import com.yahyeet.boardbook.model.entity.User;

public class ConfigureTeamPresenter {

    private ConfigureTeamAdapter playerAdapter;

    private ISelectGameFragment selectGameFragment;

    private CMMasterPresenter masterPresenter;


    public ConfigureTeamPresenter(CMMasterPresenter cm){
        this.masterPresenter = cm;
    }

    public void repopulateMatches() {
        playerAdapter.notifyDataSetChanged();
    }

    public void enableGameFeed(RecyclerView playerRecycleView, Context viewContext) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(viewContext);
        playerRecycleView.setLayoutManager(layoutManager);
        //TODO: Replace with matches from user
        User[] testSet = {new User(), new User()};
        playerAdapter = new ConfigureTeamAdapter();
        playerRecycleView.setAdapter(playerAdapter);


    }

}
