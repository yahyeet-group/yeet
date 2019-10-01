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

import org.w3c.dom.Text;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.matchViewHolder> {


    private static final String TAG = "MatchAdapter";

    // TODO Replace with match array
    private Match[] myDataset;

    public static class matchViewHolder extends RecyclerView.ViewHolder {

        // TODO Replace this area with match class as a custom view object
        private TextView winlossText;
        private TextView gameText;
        private TextView playersText;
        private ImageView imageView;

        public matchViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(v1 -> Log.d(TAG, "Element " + getAdapterPosition() + " clicked."));

            winlossText = v.findViewById(R.id.winLossView);
            gameText = v.findViewById(R.id.gameView);
            playersText = v.findViewById(R.id.)
            imageView = v.findViewById(R.id.gameImageView);
        }


    }

    // TODO replace with match instead of string
    public MatchAdapter (Match[] dataset){
        myDataset = dataset;
    }

    // Creates new view, does not assign data
    @NonNull
    @Override
    public MatchAdapter.matchViewHolder onCreateViewHolder(ViewGroup viewGroup,
                                                           int viewType) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.match_element, viewGroup, false);

        return new matchViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    // Method that assigns data to the view
    @Override
    public void onBindViewHolder(matchViewHolder holder, int position) {
        holder.winlossView.setText("Winner");
        holder.gameView.setText("Avalon?");
        //holder.imageView.setImageURI();
    }

    @Override
    public int getItemCount() {
        return myDataset.length;
    }


}
