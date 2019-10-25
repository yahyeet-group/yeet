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

/**
 * @Author Nox/Aaron Sandgren
 * This is the adapter that creates and configures the views in the PlayerRecycleView.
 */
public class PlayerAdapter extends AbstractSearchAdapter<User> implements Filterable {

	private CMMasterPresenter cmmp;
	private SelectPlayersPresenter spp;
	private List<User> usersFriends;

	/**
	 * This is the class that will be the object for every entry in the RecycleView
	 */
	class PlayerViewHolder extends RecyclerView.ViewHolder {

		TextView playerName;
		Button actionButton;

		PlayerViewHolder(View v) {
			super(v);
			playerName = v.findViewById(R.id.playerNameText);
			actionButton = v.findViewById(R.id.actionButton);


		}

		/**
		 * This is called to set the button text and color to represent one player
		 * that had been selected from before.
		 */
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

			// This makes is to so that friends of the logged in user is shown in a different color
			List<User> database = getDatabase();

			// This bind the button for selecting and deselecting the player
			// Ensuring that the button text is correct and that the user
			// Gets added to the DataObject
			vh.playerName.setText(database.get(position).getName());
			vh.actionButton.setOnClickListener(event -> {
				if (vh.actionButton.getText().toString().toLowerCase().equals("add")) {

					vh.actionButton.setText("Remove");
					vh.actionButton.setBackground(vh.itemView
						.getResources()
						.getDrawable(R.drawable.custom_button, null));

					cmmp.getCmdh().addSelectedPlayer(database.get(position));
					holder.itemView.setBackgroundColor(Color.parseColor("#0cc43d"));

				}else {
					vh.actionButton.setText("Add");
					vh.actionButton.setBackground(vh.itemView
						.getResources()
						.getDrawable(R.drawable.custom_button, null));

					cmmp.getCmdh().removeSelectedPlayer(database.get(position));
					holder.itemView.setBackgroundColor(Color.WHITE);
				}
			});

			/// If the user where to go back from the Configuring teams part of the wizard to
			/// Selecting players, the Recycle view would be reloaded and therefore the pliancy of the buttons.
			/// This fixes so that players from before now look like they should do.
			List<String> playerNames = new ArrayList<>();
			for (User user: cmmp.getCmdh().getSelectedPlayers()) {
				playerNames.add(user.getName());
			}



			if(playerNames.contains(vh.playerName.getText())){
				vh.setAlreadySelected();
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
