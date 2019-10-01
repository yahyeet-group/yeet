package com.yahyeet.boardbook.presenter;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.activity.IHomeFragment;
import com.yahyeet.boardbook.model.entity.Match;

public class HomePresenter {


    IHomeFragment view;

    public HomePresenter(IHomeFragment view){
        this.view = view;
    }


    public void repopulateMatchFeed(){
        matchAdapter.notifyDataSetChanged();
    }

    void enableMatchFeed(){
        matchRecyclerView = findViewById(R.id.home_match_recycler);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        matchRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        matchRecyclerView.setLayoutManager(layoutManager);

        Match[] temp = new Match[20];

        for(int i = 0; i < 20; i++){
            temp[i] = new Match(Integer.toString(i));
        }

        matchAdapter = new MatchAdapter(temp);
        matchRecyclerView.setAdapter(matchAdapter);
        changeTest();
    }

}
