package com.yahyeet.boardbook.presenter.friends;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.activity.profile.ProfileActivity;
import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.presenter.AbstractSearchAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for the friends recycler view
 */
public class FriendsAdapter extends AbstractSearchAdapter<User> {

	private Context context;

	static class FriendViewHolder extends RecyclerView.ViewHolder {

		private TextView friendName;
		private ImageView friendPicture;


		FriendViewHolder(View v) {
			super(v);
			// Define click listener for the ViewHolder's View.


			friendName = v.findViewById(R.id.friendName);
			friendPicture = v.findViewById(R.id.friendPicture);
		}
	}

	public FriendsAdapter(List<User> database, Context context) {
		super(database);
		this.context = context;
	}

	// Creates new view, does not assign data
	@NonNull
	@Override
	public FriendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View v = LayoutInflater
			.from(parent.getContext())
			.inflate(R.layout.element_friends, parent, false);

		return new FriendViewHolder(v);
	}

	// Replace the contents of a view (invoked by the layout manager)
	// Method that assigns data to the view
	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

		if(holder instanceof FriendViewHolder){
			FriendViewHolder vh = (FriendViewHolder) holder;
			vh.friendName.setText(getDatabase().get(position).getName());
			//holder.friendPicture.setImageURI();

			vh.itemView.setOnClickListener(v1 -> {
				Intent startProfile = new Intent(context, ProfileActivity.class);
				startProfile.putExtra("UserId", getDatabase().get(position).getId());
				context.startActivity(startProfile);
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
