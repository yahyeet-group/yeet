package com.yahyeet.boardbook.activity.matchcreation.selectplayers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
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
import com.yahyeet.boardbook.activity.matchcreation.HelperFunctions;
import com.yahyeet.boardbook.activity.matchcreation.selectgame.ISelectGameFragment;
import com.yahyeet.boardbook.presenter.matchcreation.selectplayers.SelectPlayersPresenter;

import java.util.Objects;

public class SelectPlayersFragment extends Fragment implements ISelectPlayersFragment, IFutureInteractable {

	private SelectPlayersPresenter selectPlayersPresenter;
	private SearchView searchView;
	private RecyclerView playerRecyclerView;
	private Button doneButton;
	private ProgressBar loadingIndicator;
	private TextView loadingText;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		CreateMatchActivity cma = (CreateMatchActivity) getActivity();
		selectPlayersPresenter = new SelectPlayersPresenter(this, cma.getPresenter());
		return inflater.inflate(R.layout.fragment_select_players, container, false);


	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		setViews();

		enableMatchFeed();
		enableSearchView();
		doneButton.setOnClickListener(event -> {
			selectPlayersPresenter.getMasterPresenter().goToConfigureTeams();
			this.displayLoadingFailed();
		});
	}

	public void enableMatchFeed() {
		playerRecyclerView.setHasFixedSize(true);
		selectPlayersPresenter.enableGameFeed(playerRecyclerView, Objects.requireNonNull(getView()).getContext());
	}

	public void enableSearchView() {
		selectPlayersPresenter.enableSearchBar(searchView);
	}


	private void setViews(){

		searchView = getView().findViewById(R.id.searchPlayers);
		playerRecyclerView = getView().findViewById(R.id.playerRecycleView);
		doneButton = getView().findViewById(R.id.selectPlayersDoneButton);
		loadingIndicator = getView().findViewById(R.id.spLoadingInd);
		loadingText = getView().findViewById(R.id.spTextLoading);

	}

	@Override
	public void enableViewInteraction() {
			doneButton.setEnabled(true);
			loadingIndicator.setVisibility(View.INVISIBLE);
			playerRecyclerView.setVisibility(View.VISIBLE);

			if(getContext() != null){
				playerRecyclerView.getLayoutParams().height = HelperFunctions.dpFromPx(550, getContext());
			}

			playerRecyclerView.requestLayout();
			loadingText.setVisibility(View.INVISIBLE);
	}

	@Override
	public void disableViewInteraction() {

	}

	@Override
	public void displayLoadingFailed() {
		loadingIndicator.invalidate();
		loadingText.setText("Loading Failed");
	}

}
