package com.yahyeet.boardbook.presenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.model.entity.Game;

import java.util.List;

public class GameListAdapter extends BaseAdapter {

    private Context context;
    private List<Game> gameList;

    public GameListAdapter(Context context, List<Game> gameList){
        this.context = context;
        this.gameList = gameList;
    }

    @Override
    public int getCount() {
        return gameList.size();
    }

    @Override
    public Object getItem(int position) {
        return gameList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // inflate the layout for each list row
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.game_list_element, parent, false);
        }

        Game currentItem = (Game) getItem(position);

        TextView textViewName = convertView.findViewById(R.id.gameSeachName);
        TextView textViewPlayers = convertView.findViewById(R.id.gameSearchPlayers);

        textViewName.setText(currentItem.getName());
        // TODO: Add playercount to game
        textViewPlayers.setText("5 Players");

        // returns the view for the current row
        return convertView;
    }
}
