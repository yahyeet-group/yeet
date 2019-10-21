package com.yahyeet.boardbook.presenter.game;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Filter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.activity.home.game.gamedetail.GameDetailActivity;
import com.yahyeet.boardbook.model.entity.Game;

import java.util.ArrayList;
import java.util.List;

public abstract class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder> {

	private static final String TAG = "GameAdapter";

	List<Game> dataset;
	private List<Game> allGames;
	private Context context;

	static abstract class GameViewHolder extends RecyclerView.ViewHolder {

		GameViewHolder(View v) {
			super(v);
		}
	}


	@Override
	public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
		holder.itemView.setOnClickListener(view -> {
			Intent intent = new Intent(context, GameDetailActivity.class);
			intent.putExtra("Game", dataset.get(position).getId());
			context.startActivity(intent);
		});
	}

	public GameAdapter(List<Game> dataset, List<Game> allGames, Context context) {
		if (dataset != null){
			this.dataset = dataset;
			this.allGames = allGames;
		}
		else {
			this.dataset = new ArrayList<>();
			this.allGames = new ArrayList<>();
		}
		this.context = context;
	}


	@Override
	public int getItemCount() {
		return dataset.size();
	}

	public Filter getFilter() {
		return playerFilter;
	}

	private Filter playerFilter = new Filter() {
		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			List<Game> filteredList = new ArrayList<>();
			if (constraint == null || constraint.length() == 0) {
				filteredList.addAll(allGames);
			} else {
				String filterPattern = constraint.toString().toLowerCase().trim();

				for (Game game : allGames) {
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
			dataset.clear();
			dataset.addAll((List) results.values);
			notifyDataSetChanged();
		}
	};
}
