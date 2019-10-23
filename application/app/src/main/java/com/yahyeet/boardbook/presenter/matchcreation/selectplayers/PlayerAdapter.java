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
import com.yahyeet.boardbook.presenter.AbstractSearchAdapter;
import com.yahyeet.boardbook.presenter.matchcreation.CMMasterPresenter;
import com.yahyeet.boardbook.model.entity.User;

import java.util.ArrayList;
import java.util.List;

public class PlayerAdapter extends AbstractSearchAdapter<User> implements Filterable {

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
		super(myDataset);
		this.spp = spp;
		cmmp = spp.getMasterPresenter();

	}

	@Override
	public PlayerAdapter.PlayerViewHolder onCreateViewHolder(ViewGroup vg, int viewType) {

		View v = LayoutInflater.from(vg.getContext()).inflate(R.layout.element_select_players, vg, false);


		return new PlayerViewHolder(v);

	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

		if(holder instanceof PlayerViewHolder){

			PlayerViewHolder vh = (PlayerViewHolder) holder;
			List<User> database = getDatabase();

			vh.playerName.setText(database.get(position).getName());
			vh.actionButton.setOnClickListener(event -> {
				System.out.println(vh.actionButton.getText().toString().toLowerCase());
				if (vh.actionButton.getText().toString().toLowerCase().equals("add")) {
					vh.actionButton.setText("Remove");
					cmmp.getCmdh().addSelectedPlayer(database.get(position));
				}else {
					vh.actionButton.setText("Add");
					cmmp.getCmdh().removeSelectedPlayer(database.get(position));
				}
			});
		}



	}

	@Override
	protected Filter createNewFilter() {
		return new Filter() {
			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				List<User> filteredList = new ArrayList<>();
				if (constraint == null || constraint.length() == 0) {
					filteredList.addAll(getAllEntities());
				} else {
					String filterPattern = constraint.toString().toLowerCase().trim();

					for (User user : getAllEntities()) {
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
				getDatabase().clear();
				getDatabase().addAll((List) results.values);
				notifyDataSetChanged();
			}
		};
	}
}
