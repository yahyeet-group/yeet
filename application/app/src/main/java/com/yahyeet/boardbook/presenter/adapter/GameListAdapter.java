package com.yahyeet.boardbook.presenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.model.entity.Game;

import java.util.List;

public class GameListAdapter extends GameAdapter {

    public GameListAdapter(Context context, List<Game> gameList) {
        super(context, gameList);
    }


    @Override
    protected View getInflatedView(ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.element_game_list, parent, false);
    }

    @Override
    void setupViewElements(View convertView, Game currentItem) {
        TextView textViewName = convertView.findViewById(R.id.gameSeachName);
        TextView textViewDifficulty = convertView.findViewById(R.id.gameDifficulty);
        TextView textViewPlayers = convertView.findViewById(R.id.gameListMinMaxPlayers);
        TextView textViewTeams = convertView.findViewById(R.id.gameListTeamAmount);
        textViewName.setText(currentItem.getName());
        textViewDifficulty.setText(getDifficulty(currentItem.getDifficulty()));
        textViewPlayers
                .setText(currentItem.getMinPlayers() + " - " + currentItem.getMaxPlayers() + " Players");
        textViewTeams.setText("0 - " + currentItem.getTeams().size() + " Teams");
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
