package com.yahyeet.boardbook.presenter.friends;

import android.content.Context;
import android.os.Looper;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.activity.IFutureInteractable;
import com.yahyeet.boardbook.activity.home.friends.IFriendFragment;
import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.handler.IUserHandlerListener;
import com.yahyeet.boardbook.model.handler.UserHandler;
import com.yahyeet.boardbook.presenter.BoardbookSingleton;
import com.yahyeet.boardbook.presenter.FindOnePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Presenter for the Friends view
 */
public class FriendsPresenter extends FindOnePresenter<User, UserHandler> implements IUserHandlerListener {

	private FriendsAdapter friendsAdapter;
	private IFriendFragment friendFragment;

	private List<User> userDatabase = new ArrayList<>();

	public FriendsPresenter(IFriendFragment friendFragment) {
		super((IFutureInteractable) friendFragment);
		BoardbookSingleton.getInstance().getUserHandler().addListener(this);

		this.friendFragment = friendFragment;

		updateFriends();
	}

	/**
	 * Makes recyclerView to repopulate its users with current data
	 */
	public void notifyAdapter() {
		new android.os.Handler(Looper.getMainLooper())
			.post(() -> friendsAdapter.notifyDataSetChanged());

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
	}

	@Override
	public void onUpdateUser(User entity) {
		for (int i = 0; i < userDatabase.size(); i++) {
			if (userDatabase.get(i).getId().equals(entity.getId())) {
				userDatabase.set(i, entity);
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

	public void updateFriends() {

		findEntity(
			BoardbookSingleton
				.getInstance()
				.getUserHandler(),
			BoardbookSingleton
				.getInstance()
				.getAuthHandler()
				.getLoggedInUser()
				.getId(),
			UserHandler
				.generatePopulatorConfig(true, false)
		);
	}

	@Override
	protected void onEntityFound(User entity) {
		userDatabase.clear();
		userDatabase.addAll(entity.getFriends());
		notifyAdapter();
	}
}