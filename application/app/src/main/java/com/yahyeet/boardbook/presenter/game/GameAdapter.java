package com.yahyeet.boardbook.presenter.game;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.activity.home.game.gamedetail.GameDetailActivity;
import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.presenter.AbstractSearchAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter with search functionality for recycler view in game
 */
public class GameAdapter extends AbstractSearchAdapter<Game> {

	private static final String TAG = "GameAdapter";

	private Context context;

	private static final int GAME_LIST = 0;
	private static final int GAME_GRID = 1;


	private DisplayType displayType;

	static class GameListViewHolder extends RecyclerView.ViewHolder {

		TextView textViewName;
		TextView textViewDifficulty;
		TextView textViewPlayers;
		TextView textViewTeams;


		GameListViewHolder(View v) {
			super(v);
			// Define click listener for the ViewHolder's View.
			textViewName = v.findViewById(R.id.gameSeachName);
			textViewDifficulty = v.findViewById(R.id.gameDifficulty);
			textViewPlayers = v.findViewById(R.id.gameListMinMaxPlayers);
			textViewTeams = v.findViewById(R.id.gameListTeamAmount);

		}
	}

	static class GameGridViewHolder extends RecyclerView.ViewHolder {
		TextView textViewName;

		GameGridViewHolder(View v) {
			super(v);
			// Define click listener for the ViewHolder's View.
			textViewName = v.findViewById(R.id.gameGridName);

		}
	}


	public GameAdapter(List<Game> database, Context context, DisplayType displayType) {
		super(database);
		this.context = context;
		this.displayType = displayType;
	}


	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View v;

		if (viewType == GAME_LIST) {
			v = LayoutInflater
				.from(parent.getContext())
				.inflate(R.layout.element_game_list, parent, false);
			return new GameListViewHolder(v);
		}
		v = LayoutInflater
			.from(parent.getContext())
			.inflate(R.layout.element_game_grid, parent, false);
		return new GameGridViewHolder(v);

	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
		List<Game> database = getDatabase();

		holder.itemView.setOnClickListener(view -> {
			Intent intent = new Intent(context, GameDetailActivity.class);
			intent.putExtra("Game", database.get(position).getId());
			context.startActivity(intent);
		});



		if (holder instanceof GameListViewHolder) {
			GameListViewHolder gameGridViewHolder = (GameListViewHolder) holder;

			gameGridViewHolder.textViewName.setText(database.get(position).getName());
			gameGridViewHolder.textViewDifficulty.setText(getDifficulty(database.get(position).getDifficulty()));
			gameGridViewHolder.textViewPlayers.setText(database.get(position).getMinPlayers() + " - " + database.get(position).getMaxPlayers() + " Players");
			gameGridViewHolder.textViewTeams.setText("0 - " + database.get(position).getTeams().size() + " Teams");

		} else if (holder instanceof GameGridViewHolder) {

			GameGridViewHolder gameGridViewHolder = (GameGridViewHolder) holder;

			gameGridViewHolder.textViewName.setText(database.get(position).getName());
		}


	}


	@Override
	public int getItemViewType(int position) {
		if (displayType == DisplayType.LIST)
			return GAME_LIST;
		else if (displayType == DisplayType.GRID)
			return GAME_GRID;
		else
			return super.getItemViewType(position);
	}

	public void setDisplayType(DisplayType displayType) {
		this.displayType = displayType;
	}

	@Override
	protected Filter createNewFilter() {
		return new Filter() {
			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				List<Game> filteredList = new ArrayList<>();
				if (constraint == null || constraint.length() == 0) {
					filteredList.addAll(getAllEntities());
				} else {
					String filterPattern = constraint.toString().toLowerCase().trim();

					for (Game game : getAllEntities()) {
						if (game.getName().toLowerCase().contains(filterPattern)) {
							filteredList.add(game);
						}

					}
				}

				FilterResults results = new FilterResults();
				results.values = filteredList;

				return results;
			}

			@Override
			protected void publishResults(CharSequence constraint, FilterResults results) {
				getDatabase().clear();
				getDatabase().addAll((List) results.values);
				notifyDataSetChanged();
			}
		};
	}


	private String getDifficulty(int i) {
		if (i == 1) {
			return "Easy";
		} else if (i == 2) {
			return "Medium";
		} else if (i == 3) {
			return "Hard";
		}
		return "Unknown Difficulty";
	}
}
