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
import com.yahyeet.boardbook.model.entity.Match;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.MatchViewHolder> {

    private static final String TAG = "MatchAdapter";
    private Match[] myDataset;

    static class MatchViewHolder extends RecyclerView.ViewHolder {

        // TODO Replace this area with match class as a custom view object
        private TextView winLossText;
        private TextView gameText;
        private TextView roleText;
        private TextView playersText;
        private TextView dateText;
        private ImageView imageView;


        MatchViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(v1 -> Log.d(TAG, "Element " + getAdapterPosition() + " clicked."));


            winLossText = v.findViewById(R.id.winLossView);
            gameText = v.findViewById(R.id.gameView);
            roleText = v.findViewById(R.id.roleView);
            dateText = v.findViewById(R.id.dateView);
            playersText = v.findViewById(R.id.playersView);
            imageView = v.findViewById(R.id.gameImageView);
        }


    }


    public MatchAdapter(Match[] dataset) {
        if (dataset != null)
            myDataset = dataset;
        else
            myDataset = new Match[0];
    }

    // Creates new view, does not assign data
    @NonNull
    @Override
    public MatchViewHolder onCreateViewHolder(ViewGroup viewGroup,
                                              int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.match_element, viewGroup, false);

        return new MatchViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    // Method that assigns data to the view
    @Override
    public void onBindViewHolder(MatchViewHolder holder, int position) {
        // TODO: Replace with model integration when match is implemented

        holder.winLossText.setText("Winner");
        holder.gameText.setText("Avalon?");
        holder.playersText.setText("6 Players");
        holder.dateText.setText("Some Date");
        holder.roleText.setText("(" + "Merlin" + ")");
        //holder.imageView.setImageURI();
    }

    @Override
    public int getItemCount() {
        return myDataset.length;
    }


}
