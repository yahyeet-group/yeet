package com.yahyeet.boardbook.activity.CreateingMatches.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.presenter.adapter.MatchAdapter;



public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.GamesViewHolder> {

    private Game[] myDataset;

    static class GamesViewHolder extends RecyclerView.ViewHolder {

        private TextView gameTitle;



        GamesViewHolder(View v){
            super(v);

            v.setOnClickListener((n) -> {

            });
        }

    }


    @Override
    public GamesViewHolder onCreateViewHolder(ViewGroup vg, int viewType){

        View v = LayoutInflater.from(vg.getContext()).inflate(R.layout.game_element, vg, false);

        return new GamesViewHolder(v);

    }

    @Override
    public void onBindViewHolder(GamesViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return myDataset.length;
    }

}
