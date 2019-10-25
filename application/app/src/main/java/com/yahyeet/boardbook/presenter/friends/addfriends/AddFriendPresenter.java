package com.yahyeet.boardbook.presenter.friends.addfriends;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.activity.IFutureInteractable;
import com.yahyeet.boardbook.activity.home.friends.IAddFriendActivity;
import com.yahyeet.boardbook.model.handler.UserHandler;
import com.yahyeet.boardbook.presenter.AbstractSearchAdapter;
import com.yahyeet.boardbook.presenter.FindAllPresenter;
import com.yahyeet.boardbook.presenter.BoardbookSingleton;
import com.yahyeet.boardbook.model.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class AddFriendPresenter extends FindAllPresenter<User, UserHandler> {

	private IAddFriendActivity addFriendActivity;
	private AbstractSearchAdapter searchAdapter;

	public AddFriendPresenter(IAddFriendActivity addFriendActivity) {
		super((IFutureInteractable) addFriendActivity);
		this.addFriendActivity = addFriendActivity;

		searchAdapter = new AddFriendsAdapter(getDatabase(), addFriendActivity);

		setAdapter(searchAdapter);

		fillAndModifyDatabase(BoardbookSingleton.getInstance().getUserHandler(),
			null);
	}


	/**
	 * Creates the necessary structure for populating matches
	 *
	 * @param recyclerView the RecyclerView that will be populated with matches
	 */
	public void enableAddFriendsList(RecyclerView recyclerView, Context viewContext) {
		recyclerView.setLayoutManager(new LinearLayoutManager(viewContext));
		recyclerView.setAdapter(getAdapter());
	}

	@Override
	protected void onDatabaseLoaded(List<User> database) {
		List<User> myFriends = BoardbookSingleton.getInstance().getAuthHandler().getLoggedInUser().getFriends();
		if (database != null && myFriends != null) {

			database.remove(BoardbookSingleton.getInstance().getAuthHandler().getLoggedInUser());

			List<User> notMyFriends = database
				.stream()
				.filter(user -> myFriends.stream().noneMatch(friend -> friend.getId().equals(user.getId())))
				.collect(Collectors.toList());

			setDatabase(notMyFriends);
		}
	}

	public void searchNonFriends(String query) {
		searchAdapter.getFilter().filter(query);

	}

}