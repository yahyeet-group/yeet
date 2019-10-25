package com.yahyeet.boardbook.presenter.matchcreation.selectplayers;

import android.content.res.Resources;
import android.graphics.Color;
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
	private List<User> usersFriends;


	class PlayerViewHolder extends RecyclerView.ViewHolder {

		TextView playerName;
		Button actionButton;

		PlayerViewHolder(View v) {
			super(v);
			playerName = v.findViewById(R.id.playerNameText);
			actionButton = v.findViewById(R.id.actionButton);


		}

		public void setAlreadySelected(){
			actionButton.setText("Remove");
			this.itemView.setBackgroundColor(Color.parseColor("#0cc43d"));
		}
		public String getName(){
			return playerName.getText().toString();
		}

	}

	public PlayerAdapter(List<User> myDataset, SelectPlayersPresenter spp, List<User> usersFriends) {
		super(myDataset);
		this.spp = spp;
		cmmp = spp.getMasterPresenter();
		this.usersFriends = usersFriends;




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
			if (usersFriends.contains(database.get(position))){
				vh.itemView.setBackgroundColor(Color.parseColor("#09cdda"));
			}

			vh.playerName.setText(database.get(position).getName());
			vh.actionButton.setOnClickListener(event -> {
				if (vh.actionButton.getText().toString().toLowerCase().equals("add")) {

					vh.actionButton.setText("Remove");
					vh.actionButton.setBackgroundColor(vh.itemView
						.getResources()
						.getColor(R.color.colorMatchWon, null));

					cmmp.getCmdh().addSelectedPlayer(database.get(position));
					holder.itemView.setBackgroundColor(Color.parseColor("#0cc43d"));

				}else {
					vh.actionButton.setText("Add");
					vh.actionButton.setBackground(vh.itemView
						.getResources()
						.getDrawable(R.drawable.custom_button, null));

					cmmp.getCmdh().removeSelectedPlayer(database.get(position));
					holder.itemView.setBackgroundColor(Color.WHITE);

					if (usersFriends.contains(database.get(position))){
						vh.itemView.setBackgroundColor(Color.parseColor("#09cdda"));
					}
				}
			});
		}


		/// If already selected from before this changes the pliancy and button
		List<String> playerNames = new ArrayList<>();
		for (User user: cmmp.getCmdh().getSelectedPlayers()) {
			playerNames.add(user.getName());
		}

		int amountPlayers = getItemCount();
		for (int i =0; i<amountPlayers; i++ ){
			System.out.println("does this even run");
			PlayerViewHolder holder1 = (PlayerViewHolder) holder;
			if(playerNames.contains(holder1.getName())){
				holder1.setAlreadySelected();
			}

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
