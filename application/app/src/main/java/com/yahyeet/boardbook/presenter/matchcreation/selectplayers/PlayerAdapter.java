package com.yahyeet.boardbook.presenter.matchcreation.selectplayers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.presenter.matchcreation.CMMasterPresenter;

import java.util.ArrayList;
import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> implements Filterable {

	private List<User> dataset;
	private List<User> datasetFull;
	private CMMasterPresenter cmmp;
	private SelectPlayersPresenter spp;

	class PlayerViewHolder extends RecyclerView.ViewHolder {

		TextView playerName;
		Button actionButton;

		PlayerViewHolder(View v) {
			super(v);
			playerName = v.findViewById(R.id.playerNameText);
			actionButton = v.findViewById(R.id.actionButton);

		}

	}


	public PlayerAdapter(List<User> myDataset, SelectPlayersPresenter spp) {
		this.spp = spp;
		this.dataset = myDataset;
		this.datasetFull = new ArrayList<>(dataset);
		cmmp = spp.getMasterPresenter();

	}

	@Override
	public PlayerAdapter.PlayerViewHolder onCreateViewHolder(ViewGroup vg, int viewType) {

		View v = LayoutInflater.from(vg.getContext()).inflate(R.layout.select_players_element, vg, false);


		return new PlayerViewHolder(v);

	}

	@Override
	public void onBindViewHolder(PlayerAdapter.PlayerViewHolder holder, int position) {

		holder.playerName.setText(dataset.get(position).getName());
		holder.actionButton.setOnClickListener(event -> {
			System.out.println(holder.actionButton.getText().toString().toLowerCase());
			if (holder.actionButton.getText().toString().toLowerCase().equals("add")) {
				holder.actionButton.setText("Remove");
				cmmp.getCmdh().addSelectedPlayer(dataset.get(position));
			}else {
				holder.actionButton.setText("Add");
				cmmp.getCmdh().removeSelectedPlayer(dataset.get(position));
			}
		});

	}

	@Override
	public int getItemCount() {
		return dataset.size();
	}

	@Override
	public Filter getFilter() {
		return playerFilter;
	}

	private Filter playerFilter = new Filter() {
		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			List<User> filteredList = new ArrayList<>();
			if (constraint == null || constraint.length() == 0) {
				filteredList.addAll(datasetFull);
			} else {
				String filterPattern = constraint.toString().toLowerCase().trim();

				for (User user : datasetFull) {
					if (user.getName().toLowerCase().contains(filterPattern)) {
						filteredList.add(user);
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
