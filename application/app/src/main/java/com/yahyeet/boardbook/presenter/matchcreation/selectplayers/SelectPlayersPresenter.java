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

public class SelectPlayersPresenter extends AdapterPresenter<User, UserHandler> {

	private CMMasterPresenter masterPresenter;
	private ISelectPlayersFragment spf;
	private AbstractSearchAdapter<User> searchAdapter;

	public SelectPlayersPresenter(ISelectPlayersFragment spf, CMMasterPresenter cma) {
		super((IFutureInteractable) spf);
		this.masterPresenter = cma;
		this.spf = spf;

		searchAdapter = new PlayerAdapter(getDatabase(), this);
		setAdapter(searchAdapter);

		fillDatabase(BoardbookSingleton.getInstance().getUserHandler());


	}

	public void enableGameFeed(RecyclerView gameRecycleView, Context viewContext) {
		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(viewContext);
		gameRecycleView.setLayoutManager(layoutManager);
/*
		List<User> testSet = new LinkedList<>();
		User testUser = new User();
		testUser.setName("Jaan Karm");
		testSet.add(testUser);
		User testUser2 = new User();
		testUser2.setName("Broberg Bror");
		testSet.add(testUser2);
		User testUser3 = new User();
		testUser3.setName("Rolf the Kid");
		testSet.add(testUser3);
		User testUser4 = new User();
		testUser4.setName("Daniel the Man");
		testSet.add(testUser4);*/

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
}
