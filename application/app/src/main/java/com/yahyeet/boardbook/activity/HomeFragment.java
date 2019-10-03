package com.yahyeet.boardbook.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.presenter.HomePresenter;

import javax.annotation.Nonnull;

public class HomeFragment extends Fragment implements IHomeFragment {

    private HomePresenter homePresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        homePresenter = new HomePresenter(this);
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@Nonnull View view, Bundle savedInstanceState) {
        enableMatchFeed();
    }

    /**
     * Initiates recyclerView of matches in fragment and populates it
     */
    public void enableMatchFeed() {
        // TODO: Examine how these method calls can get nullPointerException
        RecyclerView matchRecycler = getView().findViewById(R.id.homeMatchRecycler);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        matchRecycler.setHasFixedSize(true);
        homePresenter.enableMatchFeed(matchRecycler, getView().getContext());
    }

    /**
     * Orders recyclerView of matches to repopulate itself
     */
    public void repopulateMatchFeed() {
        homePresenter.repopulateMatches();
    }
}
