package com.yahyeet.boardbook.activity.matchcreation.selectgame;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.activity.matchcreation.CreateMatchActivity;
import com.yahyeet.boardbook.presenter.matchcreation.selectgame.SelectGamePresenter;

/**
 * Fragment where game is set when adding a new match
 */
public class SelectGameFragment extends Fragment implements ISelectGameFragment{

    private SelectGamePresenter selectGamePresenter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        CreateMatchActivity cma = (CreateMatchActivity)getActivity();
        selectGamePresenter = new SelectGamePresenter(this, cma.getPresenter());
        return inflater.inflate(R.layout.fragment_select_game, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        enableMatchFeed();
    }

    public void enableMatchFeed() {
        // TODO: Examine how these method calls can get nullPointerException
        RecyclerView gameRecycler = getView().findViewById(R.id.gamesRecycleView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        gameRecycler.setHasFixedSize(true);
        selectGamePresenter.enableGameFeed(gameRecycler, getView().getContext());
    }

}
