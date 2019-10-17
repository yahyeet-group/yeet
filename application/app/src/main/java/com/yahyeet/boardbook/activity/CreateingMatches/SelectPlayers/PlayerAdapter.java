package com.yahyeet.boardbook.activity.CreateingMatches.SelectPlayers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.activity.CreateingMatches.CMMasterPresenter;
import com.yahyeet.boardbook.activity.CreateingMatches.SelectGame.GamesAdapter;
import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.presenter.SelectGamePresenter;
import com.yahyeet.boardbook.presenter.SelectPlayersPresenter;

import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {

	private List<User> myDataset;
	private CMMasterPresenter cmmp;
	private SelectPlayersPresenter spp;

	class PlayerViewHolder extends RecyclerView.ViewHolder{

		PlayerViewHolder(View v){
			super(v);
		}
	}


	public PlayerAdapter(List<User> myDataset, SelectPlayersPresenter spp) {
		this.spp = spp;
		this.myDataset = myDataset;
		cmmp = spp.getMasterPresenter();

	}

	@Override
	public PlayerAdapter.PlayerViewHolder onCreateViewHolder(ViewGroup vg, int viewType) {

		View v = LayoutInflater.from(vg.getContext()).inflate(R.layout.select_players_element, vg, false);

		return new PlayerViewHolder(v);

	}

	@Override
	public void onBindViewHolder(PlayerAdapter.PlayerViewHolder holder, int position) {
	}

	@Override
	public int getItemCount() {
		return myDataset.size();
	}
}
