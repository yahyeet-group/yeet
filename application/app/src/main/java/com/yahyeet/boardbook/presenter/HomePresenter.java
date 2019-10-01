package com.yahyeet.boardbook.presenter;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.activity.IHomeFragment;
import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.presenter.adapter.MatchAdapter;

public class HomePresenter {

    private MatchAdapter matchAdapter;
    private IHomeFragment view;

    public HomePresenter(IHomeFragment view){
        this.view = view;
    }


    public void repopulateMatches(){
        matchAdapter.notifyDataSetChanged();
    }

    public void enableMatchFeed(RecyclerView matchRecyclerView){

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getFragmentContext());
        matchRecyclerView.setLayoutManager(layoutManager);


        // TODO: Replace with accessing database and displaying current users games
        Match[] temp = new Match[20];

        for(int i = 0; i < 20; i++){
            temp[i] = new Match(Integer.toString(i));
        }

        matchAdapter = new MatchAdapter(temp);
        matchRecyclerView.setAdapter(matchAdapter);
    }

}
