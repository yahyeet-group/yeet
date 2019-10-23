package com.yahyeet.boardbook.activity.matchcreation.selectplayers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.activity.IFutureInteractable;
import com.yahyeet.boardbook.activity.matchcreation.CreateMatchActivity;
import com.yahyeet.boardbook.activity.matchcreation.selectgame.ISelectGameFragment;
import com.yahyeet.boardbook.presenter.matchcreation.selectplayers.SelectPlayersPresenter;

public class SelectPlayersFragment extends Fragment implements ISelectPlayersFragment, IFutureInteractable {

	private SelectPlayersPresenter selectPlayersPresenter;
	private SearchView searchView;
	private RecyclerView playerRecyclerView;
	private Button doneButton;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		CreateMatchActivity cma = (CreateMatchActivity) getActivity();
		selectPlayersPresenter = new SelectPlayersPresenter(this, cma.getPresenter());
		return inflater.inflate(R.layout.fragment_select_players, container, false);


	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		searchView = getView().findViewById(R.id.searchPlayers);
		playerRecyclerView = getView().findViewById(R.id.playerRecycleView);
		doneButton = getView().findViewById(R.id.selectPlayersDoneButton);

		enableMatchFeed();
		enableSearchView();
		doneButton.setOnClickListener(event -> {
			selectPlayersPresenter.getMasterPresenter().goToConfigureTeams();
			this.displayLoadingFailed();
		});
	}

	public void enableMatchFeed() {
		playerRecyclerView.setHasFixedSize(true);
		selectPlayersPresenter.enableGameFeed(playerRecyclerView, getView().getContext());
	}

	public void enableSearchView() {
		selectPlayersPresenter.enableSearchBar(searchView);
	}

	@Override
	public void enableViewInteraction() {


	}

	@Override
	public void disableViewInteraction() {
//		searchView.setEnabled(false);
		//doneButton.setEnabled(false);
		//playerRecyclerView.setEnabled(false);
	}

	@Override
	public void displayLoadingFailed() {

	}

}
