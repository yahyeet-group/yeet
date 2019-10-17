package com.yahyeet.boardbook.activity.CreateingMatches.SelectPlayers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.activity.CreateingMatches.CreateMatchActivity;
import com.yahyeet.boardbook.activity.CreateingMatches.SelectGame.ISelectGameFragment;
import com.yahyeet.boardbook.presenter.SelectGamePresenter;
import com.yahyeet.boardbook.presenter.SelectPlayersPresenter;

public class SelectPlayersFragment extends Fragment implements ISelectGameFragment {

	private SelectPlayersPresenter selectPlayersPresenter;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		CreateMatchActivity cma = (CreateMatchActivity)getActivity();
		selectPlayersPresenter = new SelectPlayersPresenter(this, cma.getPresenter());
		return inflater.inflate(R.layout.fragment_select_players, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		enableMatchFeed();
	}

	public void enableMatchFeed() {
		// TODO: Examine how these method calls can get nullPointerException
		RecyclerView playerRecycler = getView().findViewById(R.id.playerRecycleView);

		// use this setting to improve performance if you know that changes
		// in content do not change the layout size of the RecyclerView
		playerRecycler.setHasFixedSize(true);
		selectPlayersPresenter.enableGameFeed(playerRecycler, getView().getContext());
	}
}
