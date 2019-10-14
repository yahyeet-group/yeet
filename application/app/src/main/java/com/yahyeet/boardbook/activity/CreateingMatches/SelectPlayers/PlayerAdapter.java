package com.yahyeet.boardbook.activity.CreateingMatches.SelectPlayers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.activity.CreateingMatches.HelperFunctions;
import com.yahyeet.boardbook.activity.CreateingMatches.SelectGame.GamesAdapter;
import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.model.entity.User;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {

	private User[] myDataset = {};



	class PlayerViewHolder extends RecyclerView.ViewHolder {


		PlayerViewHolder(View v) {
			super(v);

		}

	}

	public PlayerAdapter(User[] myDataset) {
		this.myDataset = myDataset;
	}


	@Override
	public PlayerAdapter.PlayerViewHolder onCreateViewHolder(ViewGroup vg, int viewType) {

		View v = LayoutInflater.from(vg.getContext()).inflate(R.layout.player_element, vg, false);

		return new PlayerViewHolder(v);

	}

	@Override
	public void onBindViewHolder(PlayerAdapter.PlayerViewHolder holder, int position) {

		View v = holder.itemView;

		Button editButton = v.findViewById(R.id.spEditButton);
		Button doneButton = v.findViewById(R.id.spDoneButton);

		editButton.setOnClickListener((n) -> {
			v.getLayoutParams().height = HelperFunctions.dpFromPx(250, holder.itemView.getContext());
			v.requestLayout();
		});
		doneButton.setOnClickListener((n) -> {
			System.out.println("YEEEEEEEEEEEEEET!");
			v.getLayoutParams().height = HelperFunctions.dpFromPx(100, holder.itemView.getContext());
			v.requestLayout();
		});




	}

	@Override
	public int getItemCount() {
		return myDataset.length;
	}
}
