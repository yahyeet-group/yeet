package com.yahyeet.boardbook.presenter;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.activity.CreateingMatches.CreateMatchDataHandler;
import com.yahyeet.boardbook.activity.CreateingMatches.SelectGame.ISelectGameFragment;
import com.yahyeet.boardbook.activity.CreateingMatches.ConfigureTeams.IConfigureTeamsFragment;
import com.yahyeet.boardbook.activity.CreateingMatches.ConfigureTeams.ConfigureTeamAdapter;
import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.model.entity.User;

public class ConfigureTeamPresenter {

    private ConfigureTeamAdapter playerAdapter;

    private ISelectGameFragment selectGameFragment;

    private IConfigureTeamsFragment spf;

    private CreateMatchDataHandler cmdh;

    public ConfigureTeamPresenter(IConfigureTeamsFragment spf, CreateMatchDataHandler cmdh){

        this.spf = spf;
        this.cmdh = cmdh;
    }

    public void repopulateMatches() {
        playerAdapter.notifyDataSetChanged();
    }

    public void enableGameFeed(RecyclerView playerRecycleView, Context viewContext) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(viewContext);
        playerRecycleView.setLayoutManager(layoutManager);
        //TODO: Replace with matches from user
        User[] testSet = {new User(), new User()};
        playerAdapter = new ConfigureTeamAdapter(cmdh);
        playerRecycleView.setAdapter(playerAdapter);
    }

    public void finalizeMatch(Match match){
        //TODO save this match with handlers
        //TODO change Activity back to home activity
    }
}
