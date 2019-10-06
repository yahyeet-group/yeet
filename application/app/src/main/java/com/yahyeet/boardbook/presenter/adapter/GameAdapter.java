package com.yahyeet.boardbook.presenter.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.activity.GameDetailActivity;
import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.presenter.BoardbookSingleton;

import java.util.List;

public abstract class GameAdapter extends BaseAdapter {

    protected Context context;
    private  List<Game> gameList;

    GameAdapter(Context context, List<Game> gameList){
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
            convertView = getConvertView(parent);
        }


        convertView.setOnClickListener(view -> {
            Intent intent = new Intent(context, GameDetailActivity.class);
            intent.putExtra("Game", gameList.get(position).getId());
            context.startActivity(intent);

        });

        Game currentItem = (Game) getItem(position);
        setupViewElements(convertView, currentItem);

        // returns the view for the current row
        return convertView;
    }

    abstract View getConvertView(ViewGroup parent);

    abstract void setupViewElements(View convertView, Game currentItem);
}
