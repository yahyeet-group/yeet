package com.yahyeet.boardbook.presenter.matchfeed.matchdetail;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.model.entity.MatchPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for the match player recycler view
 */
public class MatchPlayerAdapter extends RecyclerView.Adapter<MatchPlayerAdapter.MatchPlayerViewHolder>{

	private List<MatchPlayer> matchPlayers;
	private Resources resources;

	static class MatchPlayerViewHolder extends RecyclerView.ViewHolder {


		// TODO Replace this area with match class as a custom view object
		private TextView tvPlayerName;
		private TextView tvTeam;
		private ConstraintLayout constraintLayout;


		MatchPlayerViewHolder(View v) {
			super(v);
			tvPlayerName = v.findViewById(R.id.tvMatchPlayerName);
			tvTeam = v.findViewById(R.id.tvMatchPlayerInfo);
			constraintLayout = v.findViewById(R.id.matchPlayerLayout);
		}
	}

	public MatchPlayerAdapter(List<MatchPlayer> dataset, Resources resources) {
		if (dataset != null)
			matchPlayers = dataset;
		else {
			matchPlayers = new ArrayList<>();
		}

		this.resources = resources;

	}

	@NonNull
	@Override
	public MatchPlayerAdapter.MatchPlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View v = LayoutInflater
			.from(parent.getContext())
			.inflate(R.layout.element_match_player, parent, false);

		return new MatchPlayerViewHolder(v);
	}

	@Override
	public void onBindViewHolder(@NonNull MatchPlayerAdapter.MatchPlayerViewHolder holder, int position) {

		MatchPlayer current = matchPlayers.get(position);

		holder.tvPlayerName.setText(current.getUser().getName());

		if(current.getRole() != null &&
			current.getRole().getTeam() != null){
			//TODO: Add in strings.xml
			holder.tvTeam.setText("Played in " + current.getTeam().getName() + " ("  + current.getRole().getName()  + ")");
		}
		else if(current.getTeam() != null){
			holder.tvTeam.setText("Played in " + current.getTeam().getName());
		}
		else if(current.getMatch().getGame().getTeams().size() == 1){
			holder.tvTeam.setText("Played as " +  current.getRole().getName());
		}
		else{
			holder.tvTeam.setText("");
		}

		if(current.getWin()){
			holder
				.constraintLayout
				.setBackgroundColor(resources.getColor(R.color.colorMatchWon, null));
		}
		else{
			holder
				.constraintLayout
				.setBackgroundColor(resources.getColor(R.color.colorError, null));
		}



	}

	@Override
	public int getItemCount() {
		return matchPlayers.size();
	}
}
