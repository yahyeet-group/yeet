package com.yahyeet.boardbook.presenter.friends;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.activity.home.friends.IFriendFragment;
import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.presenter.BoardbookSingleton;
import com.yahyeet.boardbook.presenter.friends.FriendsAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FriendsPresenter {

	private FriendsAdapter friendsAdapter;
	// TODO: Remove if never necessary
	private IFriendFragment friendsFragment;

	final private List<User> userDatabase = new ArrayList<>();

	public FriendsPresenter(IFriendFragment friendsFragment) {
		this.friendsFragment = friendsFragment;
	}

	/**
	 * Makes recyclerView to repopulate its matches with current data
	 */
	public void repopulateFriends() {
		friendsAdapter.notifyDataSetChanged();
	}

	/**
	 * Creates the necessary structure for populating matches
	 *
	 * @param friendsRecyclerView the RecyclerView that will be populated with matches
	 */
	public void enableFriendsList(RecyclerView friendsRecyclerView, Context viewContext) {
		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(viewContext);
		friendsRecyclerView.setLayoutManager(layoutManager);

		initiateFriendPresenter();

		friendsAdapter = new FriendsAdapter(userDatabase, viewContext);
		friendsRecyclerView.setAdapter(friendsAdapter);

	}

	private void initiateFriendPresenter() {
		List<User> friends = BoardbookSingleton.getInstance().getAuthHandler().getLoggedInUser().getFriends();

		if (friends != null) {
			userDatabase.addAll(friends);
		}
	}

	public void searchFriends(String query) {
		friendsAdapter.getFilter().filter(query);
	}

}