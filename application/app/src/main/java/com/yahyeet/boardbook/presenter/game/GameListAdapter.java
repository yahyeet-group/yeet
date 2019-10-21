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

public class GameListAdapter extends GameAdapter {


    public GameListAdapter(List<Game> dataset, List<Game> allGames, Context context) {
        super(dataset, allGames, context);
    }

    static class GameListViewHolder extends GameViewHolder {

        TextView textViewName;
        TextView textViewDifficulty;
        TextView textViewPlayers;
        TextView textViewTeams;


        GameListViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            textViewName = v.findViewById(R.id.gameSeachName);
            textViewDifficulty = v.findViewById(R.id.gameDifficulty);
            textViewPlayers = v.findViewById(R.id.gameListMinMaxPlayers);
            textViewTeams = v.findViewById(R.id.gameListTeamAmount);

        }
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater
          .from(parent.getContext())
          .inflate(R.layout.element_game_list, parent, false);
        return new GameListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        GameListViewHolder gameGridViewHolder = (GameListViewHolder) holder;
        gameGridViewHolder.textViewName.setText(dataset.get(position).getName());
        gameGridViewHolder.textViewDifficulty.setText(getDifficulty(dataset.get(position).getDifficulty()));
        gameGridViewHolder.textViewPlayers.setText(dataset.get(position).getMinPlayers() + " - " + dataset.get(position).getMaxPlayers() + " Players");
        gameGridViewHolder.textViewTeams.setText("0 - " + dataset.get(position).getTeams().size() + " Teams");
    }


    private String getDifficulty(int i){
        if(i == 1){
            return "Easy";
        }
        else if(i == 2){
            return "Medium";
        }
        else if(i == 3){
            return "Hard";
        }

        return "Unknown Difficulty";
    }


}
