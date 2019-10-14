package com.yahyeet.boardbook.activity.CreateingMatches.SelectGame;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.model.entity.Game;


public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.GamesViewHolder> {

    private Game[] myDataset = {};

    class GamesViewHolder extends RecyclerView.ViewHolder {

        private TextView gameTitle;
        private ImageView gameImage;
        private Button selectGameButton;


        GamesViewHolder(View v) {
            super(v);

            gameTitle = v.findViewById(R.id.gamesTitle);
            gameImage = v.findViewById(R.id.gameImageView);
            selectGameButton = v.findViewById(R.id.selectGameButton);

            //TODO add so it changes fragment§
            selectGameButton.setOnClickListener((n) -> {
                System.out.println("Yeet --- " + v.getId());
            });
        }

    }

    public GamesAdapter(Game[] myDataset) {
        this.myDataset = myDataset;
    }


    @Override
    public GamesViewHolder onCreateViewHolder(ViewGroup vg, int viewType) {

        View v = LayoutInflater.from(vg.getContext()).inflate(R.layout.game_element, vg, false);

        return new GamesViewHolder(v);

    }

    @Override
    public void onBindViewHolder(GamesViewHolder holder, int position) {

        holder.gameTitle.setText(myDataset[position].getName());

    }

    @Override
    public int getItemCount() {
        return myDataset.length;
    }

}
