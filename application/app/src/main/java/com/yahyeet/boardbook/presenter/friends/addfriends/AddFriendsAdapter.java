package com.yahyeet.boardbook.presenter.friends.addfriends;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.activity.home.friends.IAddFriendActivity;
import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.presenter.AbstractSearchAdapter;
import com.yahyeet.boardbook.presenter.BoardbookSingleton;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for add friend recycler view
 */
public class AddFriendsAdapter extends AbstractSearchAdapter<User> {

	private IAddFriendActivity parent;

	static class AddFriendsViewHolder extends RecyclerView.ViewHolder {

		private TextView userName;
		private ImageView userPicture;
		private ImageButton btnAddFriend;

		AddFriendsViewHolder(View v) {
			super(v);
			userName = v.findViewById(R.id.userName);
			userPicture = v.findViewById(R.id.userPicture);
			btnAddFriend = v.findViewById(R.id.addFriendButton);
		}
	}

	public AddFriendsAdapter(List<User> database, IAddFriendActivity parent) {
		super(database);
		this.parent = parent;

	}

	@NonNull
	@Override
	public AddFriendsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View v = LayoutInflater
			.from(parent.getContext())
			.inflate(R.layout.element_addfriend, parent, false);

		return new AddFriendsViewHolder(v);
	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

		List<User> users = getDatabase();

		if (holder instanceof AddFriendsViewHolder) {
			AddFriendsViewHolder vh = (AddFriendsViewHolder) holder;

			vh.userName.setText(users.get(position).getName());

			vh.btnAddFriend.setOnClickListener(view -> {
				User loggedIn = BoardbookSingleton.getInstance().getAuthHandler().getLoggedInUser();
				loggedIn.addFriend(users.get(position));
				BoardbookSingleton.getInstance().getUserHandler().save(loggedIn).thenAccept(nothing -> {
					parent.finishAddFriendActivity();
				});
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
