package com.yahyeet.boardbook.activity.matchcreation.configureteams;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.activity.matchcreation.CreateMatchActivity;
import com.yahyeet.boardbook.presenter.matchcreation.configureteams.ConfigureTeamPresenter;

/**
 * Fragment that sets team, role and win/loss for a new match
 */
public class ConfigureTeamsFragment extends Fragment implements IConfigureTeamsFragment{

	private ConfigureTeamPresenter spp;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		CreateMatchActivity cma = (CreateMatchActivity)getActivity();
		spp = new ConfigureTeamPresenter(this, cma.getPresenter());
		return inflater.inflate(R.layout.fragment_configure_teams, container, false);

	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		enableMatchFeed();
	}

	public void enableMatchFeed() {
		RecyclerView gameRecycler = getView().findViewById(R.id.configureTeamsRecyleView);
		gameRecycler.setHasFixedSize(true);
		spp.enableGameFeed(gameRecycler, getView().getContext());

		getView().findViewById(R.id.matchDone).setOnClickListener(event -> {
			spp.finalizeMatch();
		});

	}
}