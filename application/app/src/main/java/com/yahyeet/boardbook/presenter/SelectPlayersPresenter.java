package com.yahyeet.boardbook.presenter;

import android.content.Context;
import android.widget.SearchView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.activity.CreateingMatches.CMMasterPresenter;
import com.yahyeet.boardbook.activity.CreateingMatches.SelectGame.GamesAdapter;
import com.yahyeet.boardbook.activity.CreateingMatches.SelectPlayers.PlayerAdapter;
import com.yahyeet.boardbook.activity.CreateingMatches.SelectPlayers.SelectPlayersFragment;
import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.model.entity.User;

import java.util.LinkedList;
import java.util.List;

public class SelectPlayersPresenter {

	private CMMasterPresenter masterPresenter;
	private SelectPlayersFragment spf;
	private PlayerAdapter playerAdapter;

	public SelectPlayersPresenter(SelectPlayersFragment spf, CMMasterPresenter cma) {
		this.masterPresenter = cma;
		this.spf = spf;


	}

	public void repopulateMatches() {
		playerAdapter.notifyDataSetChanged();
	}

	public void enableGameFeed(RecyclerView gameRecycleView, Context viewContext) {
		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(viewContext);
		gameRecycleView.setLayoutManager(layoutManager);
		//TODO: Replace with real users

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
		testSet.add(testUser4);
		playerAdapter = new PlayerAdapter(testSet, this);
		gameRecycleView.setAdapter(playerAdapter);
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
				playerAdapter.getFilter().filter(newText);
				return false;
			}
		});
	}

}
