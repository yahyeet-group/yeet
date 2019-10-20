package com.yahyeet.boardbook.presenter.game;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.model.entity.Game;

import java.util.List;

public class GameGridAdapter extends GameAdapter {


    public GameGridAdapter(List<Game> dataset, List<Game> allGames, Context context) {
        super(dataset, allGames, context);
    }

    static class GameGridViewHolder extends GameViewHolder {

        // TODO Replace this area with match class as a custom view object
        TextView textViewName;

        GameGridViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            textViewName = v.findViewById(R.id.gameGridName);

        }
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater
          .from(parent.getContext())
          .inflate(R.layout.element_game_grid, parent, false);
        return new GameGridViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        GameGridViewHolder gameGridViewHolder = (GameGridViewHolder) holder;
        gameGridViewHolder.textViewName.setText(dataset.get(position).getName());
    }
}
