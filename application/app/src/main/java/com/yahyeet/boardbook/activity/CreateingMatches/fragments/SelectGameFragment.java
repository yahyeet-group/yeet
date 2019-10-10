package com.yahyeet.boardbook.activity.CreateingMatches.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.R;

public class SelectGameFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_select_game, container, false);

    }

    @Override
    public void onStart() {
        super.onStart();

        View v = getView();

        recyclerView = v.findViewById(R.id.games_recycle_view);

        layoutManager = new LinearLayoutManager(getContext());



    }
}
