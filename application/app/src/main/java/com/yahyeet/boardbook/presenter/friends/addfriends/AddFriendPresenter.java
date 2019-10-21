package com.yahyeet.boardbook.presenter.friends.addfriends;

import android.content.Context;
import android.os.Looper;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.activity.home.friends.IAddFriendActivity;
import com.yahyeet.boardbook.model.Boardbook;
import com.yahyeet.boardbook.model.handler.UserHandler;
import com.yahyeet.boardbook.presenter.AdapterPresenter;
import com.yahyeet.boardbook.presenter.BoardbookSingleton;
import com.yahyeet.boardbook.model.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AddFriendPresenter extends AdapterPresenter<User, UserHandler> {

	private IAddFriendActivity addFriendActivity;

	final private List<User> userDatabase = new ArrayList<>();

	public AddFriendPresenter(IAddFriendActivity addFriendActivity) {
		super(addFriendActivity);
		this.addFriendActivity = addFriendActivity;

		fillAndModifyDatabase(BoardbookSingleton.getInstance().getUserHandler());
	}



	/**
	 * Creates the necessary structure for populating matches
	 *
	 * @param recyclerView the RecyclerView that will be populated with matches
	 */
	public void enableAddFriendsList(RecyclerView recyclerView, Context viewContext) {
		recyclerView.setLayoutManager(new LinearLayoutManager(viewContext));

		setAdapter(new AddFriendsAdapter(getDatabase(), addFriendActivity));
		recyclerView.setAdapter(getAdapter());
	}

	@Override
	protected void modifyDatabase(List<User> database) {
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
		getAdapter().getFilter().filter(query);

	}

}