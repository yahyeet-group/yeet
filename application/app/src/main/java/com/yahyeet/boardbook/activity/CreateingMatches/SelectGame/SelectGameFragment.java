package com.yahyeet.boardbook.activity.CreateingMatches.SelectGame;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.presenter.CreateMatchPresenter;

public class SelectGameFragment extends Fragment implements ISelectGameFragment {

    private CreateMatchPresenter createMatchPresenter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        createMatchPresenter = new CreateMatchPresenter(this);
        return inflater.inflate(R.layout.fragment_select_game, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        enableMatchFeed();
    }

    public void enableMatchFeed() {
        // TODO: Examine how these method calls can get nullPointerException
        RecyclerView gameRecycler = getView().findViewById(R.id.games_recycle_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        gameRecycler.setHasFixedSize(true);
        createMatchPresenter.enableGameFeed(gameRecycler, getView().getContext());
    }
}
