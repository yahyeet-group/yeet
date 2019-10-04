package com.yahyeet.boardbook.presenter;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.activity.IHomeFragment;
import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.presenter.adapter.MatchAdapter;

public class HomePresenter {

    private MatchAdapter matchAdapter;
    // TODO: Remove if never necessary
    private IHomeFragment homeFragment;

    public HomePresenter(IHomeFragment homeFragment) {
        this.homeFragment = homeFragment;
    }

    /**
     * Makes recyclerView to repopulate its matches with current data
     */
    public void repopulateMatches() {
        matchAdapter.notifyDataSetChanged();
    }

    /**
     * Creates the necessary structure for populating matches
     *
     * @param matchRecyclerView the RecyclerView that will be populated with matches
     */
    public void enableMatchFeed(RecyclerView matchRecyclerView, Context viewContext) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(viewContext);
        matchRecyclerView.setLayoutManager(layoutManager);
        //TODO: Replace with matches from user
        Match[] data = new Match[20];
        for(int i = 0; i < 20; i++)
            data[i] = new Match();
        matchAdapter = new MatchAdapter(data);
        matchRecyclerView.setAdapter(matchAdapter);
    }

}
