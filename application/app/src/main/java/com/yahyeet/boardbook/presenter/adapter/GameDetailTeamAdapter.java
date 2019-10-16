package com.yahyeet.boardbook.presenter.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.model.entity.GameTeam;

import java.util.ArrayList;
import java.util.List;

public class GameDetailTeamAdapter extends RecyclerView.Adapter<GameDetailTeamAdapter.GameDetailViewHolder> {


    private List<GameTeam> myDataset;



    static class GameDetailViewHolder extends RecyclerView.ViewHolder {

        // TODO Replace this area with match class as a custom view object
        private TextView gameTeamName;
        private RecyclerView roleList;

        private View view;


        GameDetailViewHolder(View v) {
            super(v);

            view = v;

            gameTeamName = v.findViewById(R.id.gameDetailTeamName);
            roleList = v.findViewById(R.id.gameDetailTeamRoleList);
        }


    }


    public GameDetailTeamAdapter(List<GameTeam> dataset) {
        if (dataset != null)
            myDataset = dataset;
        else {
            myDataset = new ArrayList<>();
        }


    }

    // Creates new view, does not assign data
    @NonNull
    @Override
    public GameDetailViewHolder onCreateViewHolder(ViewGroup viewGroup,
                                                   int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.element_game_detail_team, viewGroup, false);

        return new GameDetailViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    // Method that assigns data to the view
    @Override
    public void onBindViewHolder(GameDetailViewHolder holder, int position) {
        // TODO: Replace with model integration when game is implemented

        holder.gameTeamName.setText(myDataset.get(position).getName());


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(holder.view.getContext());
        holder.roleList.setLayoutManager(layoutManager);

        GameDetailRoleAdapter roleAdapter = new GameDetailRoleAdapter(myDataset.get(position).getRoles());

        holder.roleList.setAdapter(roleAdapter);

    }

    @Override
    public int getItemCount() {
        return myDataset.size();
    }




}
