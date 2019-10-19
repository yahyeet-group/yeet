package com.yahyeet.boardbook.presenter.matchfeed;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.activity.home.matchfeed.matchdetail.MatchDetailActivity;
import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.model.entity.MatchPlayer;
import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.util.StatisticsUtil;
import com.yahyeet.boardbook.presenter.BoardbookSingleton;

import java.util.ArrayList;
import java.util.List;


public class MatchfeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private static final String TAG = "MatchfeedAdapter";
	private static final int PROFILE_HEADER_VIEW = 1;
	private Context context;
	private List<Match> matches;

	//TODO: Naming might need to change
	private User user;
	private StatisticsUtil statisticsUtil;


	public MatchfeedAdapter(Context context, List<Match> dataset) {
		if (dataset != null)
			matches = dataset;
		else
			matches = new ArrayList<>();

		this.context = context;
	}

	public MatchfeedAdapter(Context context, List<Match> dataset, User user, StatisticsUtil statisticsUtil) {
		if (dataset != null)
			matches = dataset;
		else
			matches = new ArrayList<>();

		this.context = context;
		this.user = user;
		this.statisticsUtil = statisticsUtil;
	}


	public class HeaderViewHolder extends RecyclerView.ViewHolder {
		private TextView tvUsername;
		private TextView tvWinrate;
		private TextView tvGamesPlayed;
		private ProgressBar pbWinrate;

		HeaderViewHolder(@NonNull View v) {
			super(v);
			//Does nothing when clicked on!

			tvUsername = v.findViewById(R.id.tvUsername);
			tvWinrate = v.findViewById(R.id.tvWinrate);
			tvGamesPlayed = v.findViewById(R.id.tvGamesPlayed);
			pbWinrate = v.findViewById(R.id.pbWinrate);
		}
	}

	public class MatchViewHolder extends RecyclerView.ViewHolder {

		// TODO Replace this area with match class as a custom view object
		private TextView tvWinLost;
		private TextView tvGameName;
		private TextView tvRoleName;
		private TextView tvTeamName;
		private TextView tvPlayerAmount;
		private ImageView imageView;


		MatchViewHolder(View v) {
			super(v);
			// Define click listener for the ViewHolder's View.
			v.setOnClickListener(v1 -> Log.d(TAG, "Element " + getAdapterPosition() + " clicked."));

			tvWinLost = v.findViewById(R.id.matchWinLost);
			tvGameName = v.findViewById(R.id.matchGameName);
			tvRoleName = v.findViewById(R.id.matchRoleName);
			tvTeamName  = v.findViewById(R.id.matchTeamName);
			tvPlayerAmount = v.findViewById(R.id.matchPlayerAmount);
			imageView = v.findViewById(R.id.matchGameImage);
		}
	}


	// Creates new view, does not assign data
	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
		View v;

		if (viewType == PROFILE_HEADER_VIEW) {
			v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.element_profile_header, viewGroup, false);
			HeaderViewHolder vh = new HeaderViewHolder(v);
			return vh;
		}

		v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.element_match, viewGroup, false);
		MatchViewHolder vh = new MatchViewHolder(v);
		return vh;
	}

	// Replace the contents of a view (invoked by the layout manager)
	// Method that assigns data to the view
	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
		// TODO: Replace with model integration when match is implemented

		try {
			if (holder instanceof MatchViewHolder) {

				MatchPlayer currentMatchPlayer = matches
					.get(position)
					.getMatchPlayerByUser(BoardbookSingleton.getInstance().getAuthHandler().getLoggedInUser());

				MatchViewHolder vh = (MatchViewHolder) holder;
				vh.tvWinLost.setText(currentMatchPlayer.getWin() ? "Winner" : "Looser");
				vh.tvGameName.setText(matches.get(position).getGame().getName());
				vh.tvPlayerAmount.setText(matches.get(position).getMatchPlayers().size() + " Players");

				if(currentMatchPlayer.getTeam().getName() != null){
					vh.tvTeamName.setText("In " + currentMatchPlayer.getTeam());
					if(currentMatchPlayer.getRole().getName() != null)
						vh.tvRoleName.setText("(" + currentMatchPlayer.getRole() + ")");
				} else if(currentMatchPlayer.getTeam().getName() == null && currentMatchPlayer.getRole().getName() != null){
					vh.tvTeamName.setText("as " + currentMatchPlayer.getRole().getName());
					vh.tvRoleName.setText("");
				}



				vh.itemView.setOnClickListener(view -> {
					Intent intent = new Intent(context, MatchDetailActivity.class);
					intent.putExtra("Match", matches.get(position).getId());
					context.startActivity(intent);
				});

				//vh.imageView.setImageURL();
			} else if (holder instanceof HeaderViewHolder) {
				HeaderViewHolder vh = (HeaderViewHolder) holder;
				vh.tvUsername.setText(user.getName());
				double stats = statisticsUtil.getWinrateFromMatches(matches, user);
				int percent = (int) (100 * stats);
				vh.tvGamesPlayed.setText(user.getMatches().size());
				vh.tvWinrate.setText(percent + "%");
				vh.pbWinrate.setProgress(percent);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getItemCount() {
		if (matches == null) {
			return 0;
		}

		if (matches.size() == 0) {
			return 1;
		}

		return matches.size() + 1;
	}

	@Override
	public int getItemViewType(int position) {
		if (position == 0 && user != null) {
			return PROFILE_HEADER_VIEW;
		}
		return super.getItemViewType(position);
	}
}
