package com.yahyeet.boardbook.presenter.friends.addfriends;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.activity.home.friends.IAddFriendActivity;
import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.presenter.BoardbookSingleton;

import java.util.ArrayList;
import java.util.List;

public class AddFriendsAdapter extends RecyclerView.Adapter<AddFriendsAdapter.AddFriendsViewHolder> {

	private List<User> allUsers;
	private IAddFriendActivity parent;

	static class AddFriendsViewHolder extends RecyclerView.ViewHolder {


		// TODO Replace this area with match class as a custom view object
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

	public AddFriendsAdapter(List<User> dataset, IAddFriendActivity parent) {
		if (dataset != null)
			allUsers = dataset;
		else {
			allUsers = new ArrayList<>();
		}
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
	public void onBindViewHolder(@NonNull AddFriendsViewHolder holder, int position) {

		holder.userName.setText(allUsers.get(position).getName());

		holder.btnAddFriend.setOnClickListener(view -> {
			User loggedIn = BoardbookSingleton.getInstance().getAuthHandler().getLoggedInUser();
			loggedIn.addFriend(allUsers.get(position));
			BoardbookSingleton.getInstance().getUserHandler().save(loggedIn).thenAccept(nothing -> {
				parent.finishAddFriendActivity();
			});
		});


	}

	@Override
	public int getItemCount() {
		return allUsers.size();
	}
}
