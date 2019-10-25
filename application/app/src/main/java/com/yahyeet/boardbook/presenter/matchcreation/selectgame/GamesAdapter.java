package com.yahyeet.boardbook.presenter.matchcreation.selectgame;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.presenter.AbstractSearchAdapter;
import com.yahyeet.boardbook.presenter.matchcreation.CMMasterPresenter;
import com.yahyeet.boardbook.model.entity.Game;

import java.util.List;

/**
 * @Author Nox/Aaron Sandgren
 * This is the adapter that creates and configures the views in the GamesRecycleView.
 */
public class GamesAdapter extends AbstractSearchAdapter<Game> {

    private SelectGamePresenter sgp;
    private CMMasterPresenter cmmp;


    class GamesViewHolder extends RecyclerView.ViewHolder {

        private TextView gameTitle;
        private ImageView gameImage;
        private Button selectGameButton;


        GamesViewHolder(View v) {
            super(v);

            gameTitle = v.findViewById(R.id.gamesTitle);
            gameImage = v.findViewById(R.id.selectGameImage);
            selectGameButton = v.findViewById(R.id.selectGameButton);

        }
    }

    public GamesAdapter(List<Game> dataset, SelectGamePresenter sgp) {
        super(dataset);
        this.sgp = sgp;
        cmmp = sgp.getMasterPresenter();

    }


    @Override
    public GamesViewHolder onCreateViewHolder(ViewGroup vg, int viewType) {

        View v = LayoutInflater.from(vg.getContext()).inflate(R.layout.element_select_game, vg, false);

        return new GamesViewHolder(v);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // Binds the button in the entries
        if(holder instanceof GamesViewHolder){
            GamesViewHolder vh = (GamesViewHolder) holder;
            vh.gameTitle.setText(getDatabase().get(position).getName());
            vh.itemView.findViewById(R.id.selectGameButton).setOnClickListener((event)->{
                cmmp.goToSelectPlayers();
                cmmp.getCmdh().setGame(getDatabase().get(position));
            });
        }



    }

    @Override
    protected Filter createNewFilter() {
        return null;
    }

}
