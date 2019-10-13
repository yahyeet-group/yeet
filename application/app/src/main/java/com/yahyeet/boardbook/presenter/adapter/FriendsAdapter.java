package com.yahyeet.boardbook.presenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.activity.ProfileActivity;
import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.model.entity.User;

import java.util.ArrayList;
import java.util.List;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.FriendViewHolder> {

	private static final String TAG = "FriendsAdapter";
	private List<User> myDataset;
	private Context context;

	static class FriendViewHolder extends RecyclerView.ViewHolder {

		// TODO Replace this area with match class as a custom view object
		private TextView friendName;
		private ImageView friendPicture;


		FriendViewHolder(View v) {
			super(v);
			// Define click listener for the ViewHolder's View.


			friendName = v.findViewById(R.id.friendName);
			friendPicture = v.findViewById(R.id.friendPicture);
		}


	}


	public FriendsAdapter(List<User> dataset, Context context) {
		if (dataset != null)
			myDataset = dataset;
		else {
			myDataset = new ArrayList<>();
		}
		this.context = context;
	}

	// Creates new view, does not assign data
	@NonNull
	@Override
	public FriendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View v = LayoutInflater
			.from(parent.getContext())
			.inflate(R.layout.friends_element, parent, false);

		return new FriendViewHolder(v);
	}

	// Replace the contents of a view (invoked by the layout manager)
	// Method that assigns data to the view
	@Override
	public void onBindViewHolder(FriendViewHolder holder, int position) {


		holder.friendName.setText(myDataset.get(position).getName());
		//holder.friendPicture.setImageURI();

		holder.itemView.setOnClickListener(v1 -> {
			Intent startProfile = new Intent(context, ProfileActivity.class);
			startProfile.putExtra("UserId", myDataset.get(position).getId());
			context.startActivity(startProfile);
		});

	}

	@Override
	public int getItemCount() {
		return myDataset.size();
	}
}
