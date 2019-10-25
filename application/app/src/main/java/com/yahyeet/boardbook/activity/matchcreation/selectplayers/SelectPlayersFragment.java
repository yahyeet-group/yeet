package com.yahyeet.boardbook.activity.matchcreation.selectplayers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.activity.matchcreation.CreateMatchActivity;
import com.yahyeet.boardbook.activity.matchcreation.selectgame.ISelectGameFragment;
import com.yahyeet.boardbook.presenter.matchcreation.selectplayers.SelectPlayersPresenter;

/**
 * Fragment for selecting what players played in a match
 */
public class SelectPlayersFragment extends Fragment implements ISelectGameFragment {

	private SelectPlayersPresenter selectPlayersPresenter;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		CreateMatchActivity cma = (CreateMatchActivity) getActivity();
		selectPlayersPresenter = new SelectPlayersPresenter(this, cma.getPresenter());
		return inflater.inflate(R.layout.fragment_select_players, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		enableMatchFeed();
		enableSearchView();
		this.getView().findViewById(R.id.selectPlayersDoneButton).setOnClickListener(event -> {
					selectPlayersPresenter.getMasterPresenter().goToConfigureTeams();
			System.out.println(selectPlayersPresenter.getMasterPresenter().getCmdh().getSelectedPlayers().toString());
			});
	}

	public void enableMatchFeed() {
		RecyclerView playerRecycler = getView().findViewById(R.id.playerRecycleView);
		playerRecycler.setHasFixedSize(true);
		selectPlayersPresenter.enableGameFeed(playerRecycler, getView().getContext());
	}

	public void enableSearchView(){
		SearchView searchView = getView().findViewById(R.id.searchPlayers);
		selectPlayersPresenter.enableSearchBar(searchView);
	}
}
