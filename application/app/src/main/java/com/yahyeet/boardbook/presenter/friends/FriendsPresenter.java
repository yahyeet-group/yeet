package com.yahyeet.boardbook.presenter.friends;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.activity.home.friends.IFriendFragment;
import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.handler.IUserHandlerListener;
import com.yahyeet.boardbook.presenter.BoardbookSingleton;

import java.util.List;

/**
 * Presenter for the Friends view
 */
public class FriendsPresenter implements IUserHandlerListener {

	private FriendsAdapter friendsAdapter;

	final private List<User> userDatabase;

	public FriendsPresenter() {
		userDatabase = BoardbookSingleton.getInstance().getAuthHandler().getLoggedInUser().getFriends();
		BoardbookSingleton.getInstance().getUserHandler().addListener(this);
	}

	/**
	 * Makes recyclerView to repopulate its users with current data
	 */
	public void notifyAdapter() {
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

		friendsAdapter = new FriendsAdapter(userDatabase, viewContext);
		friendsRecyclerView.setAdapter(friendsAdapter);

	}

	public void searchFriends(String query) {
		friendsAdapter.getFilter().filter(query);
	}

	@Override
	public void onAddUser(User user) {
		// New users don't automatically become friends
		notifyAdapter();
	}

	@Override
	public void onUpdateUser(User user) {
		for (int i = 0; i < userDatabase.size(); i++) {
			if (userDatabase.get(i).getId().equals(user.getId())) {
				userDatabase.set(i, user);
			}
		}
		notifyAdapter();
	}

	@Override
	public void onRemoveUser(String id) {
		for (int i = 0; i < userDatabase.size(); i++) {
			if (userDatabase.get(i).getId().equals(id)) {
				userDatabase.remove(i);
				break;
			}
		}
		notifyAdapter();
	}
}