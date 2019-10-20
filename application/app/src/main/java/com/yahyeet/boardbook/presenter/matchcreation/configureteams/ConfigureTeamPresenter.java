package com.yahyeet.boardbook.presenter.matchcreation.configureteams;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.activity.matchcreation.configureteams.ConfigureTeamsFragment;
import com.yahyeet.boardbook.activity.matchcreation.configureteams.IConfigureTeamsFragment;
import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.presenter.matchcreation.CMMasterPresenter;

public class ConfigureTeamPresenter {

    private ConfigureTeamAdapter configureTeamAdapter;

    private IConfigureTeamsFragment configureTeamsFragment;

    private CMMasterPresenter masterPresenter;


    public ConfigureTeamPresenter(ConfigureTeamsFragment ctf,CMMasterPresenter cm){
        this.configureTeamsFragment = ctf;
        this.masterPresenter = cm;
    }

    public void repopulateMatches() {
        configureTeamAdapter.notifyDataSetChanged();
    }

    public void enableGameFeed(RecyclerView playerRecycleView, Context viewContext) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(viewContext);
        playerRecycleView.setLayoutManager(layoutManager);
        //TODO: Replace with matches from user
        User[] testSet = {new User(), new User()};
        configureTeamAdapter = new ConfigureTeamAdapter(this);
        playerRecycleView.setAdapter(configureTeamAdapter);
    }

    public CMMasterPresenter getMasterPresenter(){ return masterPresenter;}

}
