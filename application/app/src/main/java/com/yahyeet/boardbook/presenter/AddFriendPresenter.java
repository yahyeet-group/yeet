package com.yahyeet.boardbook.presenter;

import android.content.Context;
import android.os.Looper;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.activity.FriendsActivity.IAddFriendActivity;
import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.presenter.adapter.AddFriendsAdapter;

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
	public void repopulateFriends() {
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

		initiateAddFriendPresenter();

		addFriendsAdapter = new AddFriendsAdapter(userDatabase);
		matchRecyclerView.setAdapter(addFriendsAdapter);
	}

	private void initiateAddFriendPresenter() {

		addFriendActivity.disableActivityInteraction();
		List<User> myFriends = BoardbookSingleton.getInstance().getAuthHandler().getLoggedInUser().getFriends();


		BoardbookSingleton.getInstance().getUserHandler().all().thenAccept(allUsers -> {
			if (allUsers != null && myFriends != null) {
				List<User> notMyFriends = allUsers
					.stream()
					.filter(user -> myFriends.stream().noneMatch(friend -> friend.getId().equals(user.getId())))
					.collect(Collectors.toList());

				userDatabase.addAll(notMyFriends);
				all.addAll(notMyFriends);


				new android.os.Handler(Looper.getMainLooper()).post(() -> {
					addFriendActivity.enableActivityInteraction();
					repopulateFriends();
				});

			}
		}).exceptionally(e -> {
				new android.os.Handler(Looper.getMainLooper()).post(() -> {
					addFriendActivity.enableActivityInteraction();
					addFriendActivity.showErrorMessage();
				});
				return null;
			}
		);


	}


	public void searchNonFriends(String query) {
		List<User> temp = findMatchingName(all, query);
		userDatabase.clear();
		userDatabase.addAll(temp);
		repopulateFriends();

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