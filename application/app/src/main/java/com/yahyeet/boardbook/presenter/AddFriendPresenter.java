package com.yahyeet.boardbook.presenter;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.activity.FriendsActivity.IAddFriendActivity;
import com.yahyeet.boardbook.activity.FriendsActivity.IFriendFragment;
import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.presenter.adapter.AddFriendsAdapter;
import com.yahyeet.boardbook.presenter.adapter.FriendsAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AddFriendPresenter {

	private AddFriendsAdapter addFriendsAdapter;
	// TODO: Remove if never necessary
	private IAddFriendActivity addFriendActivity;

	final private List<User> userDatabase = new ArrayList<>();
	private List<User> all = new ArrayList<>();

	public AddFriendPresenter(IAddFriendActivity addFriendActivity) {
		this.addFriendActivity = addFriendActivity;
	}

	/**
	 * Makes recyclerView to repopulate its matches with current data
	 */
	public void repopulateMatches() {
		addFriendsAdapter.notifyDataSetChanged();
	}

	/**
	 * Creates the necessary structure for populating matches
	 *
	 * @param matchRecyclerView the RecyclerView that will be populated with matches
	 */
	public void enableAddFriendsList(RecyclerView matchRecyclerView, Context viewContext) {
		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(viewContext);
		matchRecyclerView.setLayoutManager(layoutManager);


		for (int i = 0; i < 30; i++) {
			userDatabase.add(new User(Integer.toString(i), "Name: " + i));

		}

		all.addAll(userDatabase);

		addFriendsAdapter = new AddFriendsAdapter(userDatabase);
		matchRecyclerView.setAdapter(addFriendsAdapter);
	}

	private void initiateAddFriendPresenter() {

		addFriendActivity.disableActivityInteraction();
		//List<User> friends = BoardbookSingleton.getInstance().getAuthHandler().getLoggedInUser().getFriends();
		//userDatabase.addAll(friends);
		//all = friends;
		addFriendActivity.enableActivityInteraction();
		repopulateMatches();
	}

	public void searchNonFriends(String query) {
		List<User> temp = findMatchingName(all, query);
		userDatabase.clear();
		userDatabase.addAll(temp);
		repopulateMatches();

	}

	private List<User> findMatchingName(List<User> users, String query) {

		if (query == null)
			return users;

		if (users == null) {
			return new ArrayList<>();
		}

		return users.stream().filter(user -> user.getName().toLowerCase().contains(query)).collect(Collectors.toList());
	}


}