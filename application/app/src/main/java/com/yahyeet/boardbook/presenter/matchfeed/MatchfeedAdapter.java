package com.yahyeet.boardbook.presenter.matchfeed;

import android.content.Context;
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
import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.util.StatisticsUtil;

import java.util.ArrayList;
import java.util.List;


public class MatchfeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private static final String TAG = "MatchfeedAdapter";
	private static final int PROFILE_HEADER_VIEW = 1;
	private Context context;
	private List<Match> myDataset;

	//TODO: Naming might need to change
	private User user;
	private StatisticsUtil statisticsUtil;


	public MatchfeedAdapter(List<Match> dataset) {
		if (dataset != null)
			myDataset = dataset;
		else
			myDataset = new ArrayList<>();
	}

	public MatchfeedAdapter(Context context, List<Match> dataset, User user, StatisticsUtil statisticsUtil) {
		if (dataset != null)
			myDataset = dataset;
		else
			myDataset = new ArrayList<>();

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
				MatchViewHolder vh = (MatchViewHolder) holder;
				vh.winLossText.setText("Winner");
				vh.gameText.setText("Avalon?");
				vh.playersText.setText("6 Players");
				vh.dateText.setText("Some Date");
				vh.roleText.setText("(" + "Merlin" + ")");
				//vh.imageView.setImageURL();
			} else if (holder instanceof HeaderViewHolder) {
				HeaderViewHolder vh = (HeaderViewHolder) holder;
				vh.tvUsername.setText(user.getName());
				double stats = statisticsUtil.getWinrateFromMatches(myDataset, user);
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
		if (myDataset == null) {
			return 0;
		}

		if (myDataset.size() == 0) {
			return 1;
		}

		return myDataset.size() + 1;
	}

	@Override
	public int getItemViewType(int position) {
		if (position == 0 && user != null) {
			return PROFILE_HEADER_VIEW;
		}
		return super.getItemViewType(position);
	}
}
