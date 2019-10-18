package com.yahyeet.boardbook.activity.home.game;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.model.entity.Game;

import java.util.List;

public class GameGridAdapter extends GameAdapter {


    public GameGridAdapter(Context context, List<Game> gameList) {
        super(context, gameList);
    }

    @Override
    View getInflatedView(ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.element_game_grid, parent, false);
    }

    @Override
    void setupViewElements(View convertView, Game currentItem) {
        TextView textViewName = convertView.findViewById(R.id.gameGridName);

        textViewName.setText(currentItem.getName());
    }
}
