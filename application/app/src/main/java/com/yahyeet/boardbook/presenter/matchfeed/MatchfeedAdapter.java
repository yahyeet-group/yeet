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


/**
 * Adapter for the match feed recycler view
 */
public class MatchfeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private static final String TAG = "MatchfeedAdapter";
	private static final int PROFILE_HEADER_VIEW = 1;
	private Context context;
	private List<Match> matches;
	private User currentUser;
	private boolean isProfile = false;

	private StatisticsUtil statisticsUtil;


	public MatchfeedAdapter(Context context, List<Match> dataset, User currentUser) {
		if (dataset != null)
			matches = dataset;
		else
			matches = new ArrayList<>();
		this.currentUser = currentUser;

		this.context = context;
	}

	public MatchfeedAdapter(Context context, List<Match> dataset, User user, StatisticsUtil statisticsUtil) {
		if (dataset != null)
			matches = dataset;
		else
			matches = new ArrayList<>();
		currentUser = user;
		this.context = context;
		this.statisticsUtil = statisticsUtil;

		if(currentUser != null)
			isProfile = true;
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
			return new HeaderViewHolder(v);
		}
		v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.element_match, viewGroup, false);
		return new MatchViewHolder(v);
	}

	// Replace the contents of a view (invoked by the layout manager)
	// Method that assigns data to the view
	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


		if(isProfile)
			// Position - 1 to align match list with position,
			position -= 1;

		try {
			if (holder instanceof MatchViewHolder) {
				// For onClickListener lambda expression
				int finalPosition = position;

				MatchPlayer currentMatchPlayer = matches
					.get(position)
					.getMatchPlayerByUser(currentUser);

				MatchViewHolder vh = (MatchViewHolder) holder;
				vh.tvWinLost.setText(currentMatchPlayer.getWin() ? "Winner" : "Looser");
				vh.tvGameName.setText(matches.get(position).getGame().getName());
				vh.tvPlayerAmount.setText(matches.get(position).getMatchPlayers().size() + " Players");

				if(currentMatchPlayer.getTeam() != null){
					vh.tvTeamName.setText("In " + currentMatchPlayer.getTeam().getName());
					if(currentMatchPlayer.getRole() != null)
						vh.tvRoleName.setText("(" + currentMatchPlayer.getRole().getName() + ")");
					else
						vh.tvRoleName.setText("");
				} else if(currentMatchPlayer.getRole() != null){
					vh.tvTeamName.setText("as " + currentMatchPlayer.getRole().getName());
					vh.tvRoleName.setText("");
				}
				else{
					vh.tvRoleName.setText("");
					vh.tvTeamName.setText("");
				}


				vh.itemView.setOnClickListener(view -> {
					Intent intent = new Intent(context, MatchDetailActivity.class);
					intent.putExtra("Match", matches.get(finalPosition).getId());
					context.startActivity(intent);
				});

				//vh.imageView.setImageURL();
			} else if (holder instanceof HeaderViewHolder) {
				HeaderViewHolder vh = (HeaderViewHolder) holder;
				vh.tvUsername.setText(currentUser.getName());
				double stats = statisticsUtil.getWinrateFromMatches(currentUser.getMatches(), currentUser);
				int percent = (int) (100 * stats);
				vh.tvGamesPlayed.setText(Integer.toString(currentUser.getMatches().size()));
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

		if(!isProfile)
			return matches.size();

		return matches.size() + 1;
	}

	@Override
	public int getItemViewType(int position) {
		if (position == 0 && isProfile) {
			return PROFILE_HEADER_VIEW;
		}
		return super.getItemViewType(position);
	}
}
