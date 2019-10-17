package com.yahyeet.boardbook.activity.CreateingMatches.SelectGame;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.activity.CreateingMatches.CMMasterPresenter;
import com.yahyeet.boardbook.activity.CreateingMatches.CreateMatchActivity;
import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.presenter.SelectGamePresenter;


public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.GamesViewHolder> {

    private Game[] myDataset = {};
    private SelectGamePresenter sgp;
    private CMMasterPresenter cmmp;


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

    public GamesAdapter(Game[] myDataset, SelectGamePresenter sgp) {
        this.sgp = sgp;
        this.myDataset = myDataset;
        cmmp = sgp.getMasterPresenter();

    }


    @Override
    public GamesViewHolder onCreateViewHolder(ViewGroup vg, int viewType) {

        View v = LayoutInflater.from(vg.getContext()).inflate(R.layout.game_element, vg, false);

        return new GamesViewHolder(v);

    }

    @Override
    public void onBindViewHolder(GamesViewHolder holder, int position) {

        holder.gameTitle.setText(myDataset[position].getName());
        holder.itemView.findViewById(R.id.selectGameButton).setOnClickListener((event)->{
            cmmp.goToSelectPlayers();
            cmmp.getCmdh().setGame(myDataset[position]);
        });

    }

    @Override
    public int getItemCount() {
        return myDataset.length;
    }

}
