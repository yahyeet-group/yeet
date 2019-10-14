package com.yahyeet.boardbook.activity.CreateingMatches.SelectPlayers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.presenter.SelectPlayerPresenter;

import java.util.ArrayList;
import java.util.List;

public class SelectPlayersFragment extends Fragment implements ISelectPlayersFragment {

	private SelectPlayerPresenter spp;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		spp = new SelectPlayerPresenter(this);
		return inflater.inflate(R.layout.fragment_select_players, container, false);

	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		enableMatchFeed();
	}

	public void enableMatchFeed() {
		// TODO: Examine how these method calls can get nullPointerException
		RecyclerView gameRecycler = getView().findViewById(R.id.player_recycle_view);

		// use this setting to improve performance if you know that changes
		// in content do not change the layout size of the RecyclerView
		gameRecycler.setHasFixedSize(true);
		spp.enableGameFeed(gameRecycler, getView().getContext());
	}

	private void bindTestButtons() {




		//// SPINNER INIT
		List<String> spinnerArray = new ArrayList<String>();
		spinnerArray.add("Minions Of Mordred");
	//	Spinner teamSpinner = v.findViewById(R.id.teamSpinner);
		ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, spinnerArray);    // Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
	//	teamSpinner.setAdapter(adapter);

	}


}