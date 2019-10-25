package com.yahyeet.boardbook.presenter.matchcreation.selectplayers;

import android.content.Context;
import android.widget.SearchView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.activity.IFutureInteractable;
import com.yahyeet.boardbook.activity.matchcreation.selectplayers.ISelectPlayersFragment;
import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.handler.UserHandler;
import com.yahyeet.boardbook.presenter.AbstractSearchAdapter;
import com.yahyeet.boardbook.presenter.AdapterPresenter;
import com.yahyeet.boardbook.presenter.BoardbookSingleton;
import com.yahyeet.boardbook.presenter.matchcreation.CMMasterPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @Author Nox/Aaron Sandgren
 * This is the Presenter for the SelectingPlayers fragment.
 * This class binds references to the MasterPresenter, Enables the RecycleView and gives it the correct adapter
 * Enables the SearchBar by configuring its eventListener and collects some data from the database to use in the adapter class
 */
public class SelectPlayersPresenter extends AdapterPresenter<User, UserHandler> {

	private CMMasterPresenter masterPresenter;
	private AbstractSearchAdapter<User> searchAdapter;
	private List<User> friends = new ArrayList<>();

	public SelectPlayersPresenter(ISelectPlayersFragment spf, CMMasterPresenter cma) {
		super((IFutureInteractable) spf);
		this.masterPresenter = cma;

		searchAdapter = new PlayerAdapter(getDatabase(), this, friends);
		setAdapter(searchAdapter);

		fillAndModifyDatabase(BoardbookSingleton.getInstance().getUserHandler());


	}

	public void enableGameFeed(RecyclerView gameRecycleView, Context viewContext) {
		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(viewContext);
		gameRecycleView.setLayoutManager(layoutManager);
		gameRecycleView.setAdapter(getAdapter());
	}

	public CMMasterPresenter getMasterPresenter() {
		return masterPresenter;
	}

	public void enableSearchBar(SearchView searchView){

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
	protected void modifyDatabase(List<User> database) {

		User loggedInUser = BoardbookSingleton.getInstance().getAuthHandler().getLoggedInUser();

		List<User> notFriends = database.stream().filter(user -> {
			if(user.equals(loggedInUser))
				return false;

			if(loggedInUser
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

	}
}
