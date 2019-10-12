package com.yahyeet.boardbook.presenter.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.model.entity.GameRole;
import com.yahyeet.boardbook.model.entity.Match;

import java.util.ArrayList;
import java.util.List;

public class GameDetailRoleAdapter extends RecyclerView.Adapter<GameDetailRoleAdapter.RoleViewHolder>{

    private List<GameRole> myDataset;

    static class RoleViewHolder extends RecyclerView.ViewHolder {


        TextView roleName;

        RoleViewHolder(View v) {
            super(v);

            roleName = v.findViewById(R.id.gameDetailRoleName);
        }


    }


    public GameDetailRoleAdapter(List<GameRole> dataset) {
        if (dataset != null)
            myDataset = dataset;
        else{
            myDataset = new ArrayList<>();
        }


    }

    // Creates new view, does not assign data
    @NonNull
    @Override
    public RoleViewHolder onCreateViewHolder(ViewGroup viewGroup,
                                                           int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.game_detail_role_element, viewGroup, false);

        return new RoleViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    // Method that assigns data to the view
    @Override
    public void onBindViewHolder(@NonNull GameDetailRoleAdapter.RoleViewHolder holder, int position) {

        holder.roleName.setText(myDataset.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return myDataset.size();
    }


}
