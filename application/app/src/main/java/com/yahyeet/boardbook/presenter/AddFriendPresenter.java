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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
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

		addFriendsAdapter = new AddFriendsAdapter(userDatabase);
		matchRecyclerView.setAdapter(addFriendsAdapter);
	}

	private void initiateAddFriendPresenter() {

		addFriendActivity.disableActivityInteraction();
		List<User> myFriends = BoardbookSingleton.getInstance().getAuthHandler().getLoggedInUser().getFriends();

		List<User> notMyFriends = new ArrayList<>();
		//TODO: Rosen help
		try {
			notMyFriends = BoardbookSingleton.getInstance().getUserHandler().all().get();
		} catch (ExecutionException | InterruptedException e) {
			e.printStackTrace();
		}

		if (notMyFriends != null && myFriends != null) {
			notMyFriends.removeAll(myFriends);

		}


		userDatabase.addAll(notMyFriends);
		all.addAll(notMyFriends);

		addFriendActivity.enableActivityInteraction();
		//userDatabase.addAll(friends);
		//all = friends;

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