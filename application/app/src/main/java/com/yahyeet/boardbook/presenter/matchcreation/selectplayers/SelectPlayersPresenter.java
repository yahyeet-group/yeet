package com.yahyeet.boardbook.presenter.matchcreation.selectplayers;

import android.content.Context;
import android.os.Looper;
import android.widget.SearchView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.activity.IFutureInteractable;
import com.yahyeet.boardbook.activity.matchcreation.selectplayers.ISelectPlayersFragment;
import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.handler.UserHandler;
import com.yahyeet.boardbook.presenter.AbstractSearchAdapter;
import com.yahyeet.boardbook.presenter.FindAllPresenter;
import com.yahyeet.boardbook.presenter.BoardbookSingleton;
import com.yahyeet.boardbook.presenter.matchcreation.CMMasterPresenter;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This is the Presenter for the SelectingPlayers fragment.
 * This class binds references to the MasterPresenter, Enables the RecycleView and gives it the correct adapter
 * Enables the SearchBar by configuring its eventListener and collects some data from the database to use in the adapter class
 */
public class SelectPlayersPresenter extends FindAllPresenter<User, UserHandler> {

	private CMMasterPresenter masterPresenter;
	private AbstractSearchAdapter<User> searchAdapter;
	private List<User> friends = new ArrayList<>();
	private ISelectPlayersFragment selectPlayersFragment;

	public SelectPlayersPresenter(ISelectPlayersFragment spf, CMMasterPresenter cma) {
		super((IFutureInteractable) spf);
		selectPlayersFragment = spf;
		this.masterPresenter = cma;

		User loggedInUser = BoardbookSingleton
			.getInstance()
			.getAuthHandler()
			.getLoggedInUser();

		if (!masterPresenter.getCmdh().getSelectedPlayers().contains(loggedInUser))
			masterPresenter
				.getCmdh()
				.addSelectedPlayer(loggedInUser);


		searchAdapter = new PlayerAdapter(getDatabase(), this, friends);
		setAdapter(searchAdapter);

		fillAndModifyDatabase(BoardbookSingleton.getInstance().getUserHandler(),
			UserHandler.generatePopulatorConfig(true, false));

	}

	public void enableGameFeed(RecyclerView gameRecycleView, Context viewContext) {
		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(viewContext);
		gameRecycleView.setLayoutManager(layoutManager);
		gameRecycleView.setAdapter(getAdapter());
	}

	public CMMasterPresenter getMasterPresenter() {
		return masterPresenter;
	}

	public void enableSearchBar(SearchView searchView) {

		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				searchAdapter.getFilter().filter(newText);
				return false;
			}
		});


	}

	@Override
	protected void onDatabaseModify(List<User> database) {

		User notPopulatedLoggedInUser = BoardbookSingleton.getInstance().getAuthHandler().getLoggedInUser();
		BoardbookSingleton.getInstance().getUserHandler().find(notPopulatedLoggedInUser.getId(), UserHandler.generatePopulatorConfig(true, false)).thenAccept(loggedInUser -> {
			List<User> notFriends = database.stream().filter(user -> {
				if (user.equals(loggedInUser))
					return false;

				if (loggedInUser
					.getFriends()
					.stream()
					.anyMatch(friend -> friend.equals(user))) {
					return false;
				}

				return true;
			}).collect(Collectors.toList());


			database.clear();

			database.add(loggedInUser);

			database.addAll(loggedInUser
				.getFriends()
				.stream()
				.sorted((left, right) -> left.getName().compareTo(right.getName()))
				.collect(Collectors.toList()));


			friends.addAll(loggedInUser
				.getFriends()
				.stream()
				.sorted((left, right) -> left.getName().compareTo(right.getName()))
				.collect(Collectors.toList()));

			database.addAll(notFriends
				.stream()
				.sorted((left, right) -> left.getName().compareTo(right.getName()))
				.collect(Collectors.toList()));

			new android.os.Handler(Looper.getMainLooper()).post(() -> selectPlayersFragment.enablePlayerAdapter());

		});




	}
}
