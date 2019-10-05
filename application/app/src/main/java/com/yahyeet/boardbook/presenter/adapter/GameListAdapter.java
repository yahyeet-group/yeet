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
    protected View getConvertView(ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.game_list_element, parent, false);
    }

    @Override
    void setupViewElements(View convertView, Game currentItem) {
        TextView textViewName = convertView.findViewById(R.id.gameSeachName);
        TextView textViewPlayers = convertView.findViewById(R.id.gameListTime);

        textViewName.setText(currentItem.getName());
        textViewPlayers.setText("20 min");
        // TODO: Add game time, rating? and difficulty to model
    }


}
