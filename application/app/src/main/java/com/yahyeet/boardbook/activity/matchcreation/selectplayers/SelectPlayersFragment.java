package com.yahyeet.boardbook.activity.matchcreation.selectplayers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.activity.IFutureInteractable;
import com.yahyeet.boardbook.activity.matchcreation.CreateMatchActivity;
import com.yahyeet.boardbook.activity.viewutils.ViewUtils;
import com.yahyeet.boardbook.presenter.matchcreation.selectplayers.SelectPlayersPresenter;

import java.util.Objects;

/**
 * @Author Nox/Aaron Sandgren
 * This is the fragment for the step of selecting players in the Wizard.
 * This instantiates the the SelectPlayerPresenter for the class and
 * starts up the RecycleView and the Search functionality
 */
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

		enableSearchView();
		doneButton.setOnClickListener(event -> {
			selectPlayersPresenter.getMasterPresenter().goToConfigureTeams();
		});
	}


	@Override
	public void enablePlayerAdapter() {
		playerRecyclerView.setHasFixedSize(true);
		if(getView() != null)
			selectPlayersPresenter.enableGameFeed(playerRecyclerView, getView().getContext());
	}

	public void enableSearchView() {
		selectPlayersPresenter.enableSearchBar(searchView);
	}

	// This could be done in constructor but this reduces clutter
	private void setViews() {

		searchView = getView().findViewById(R.id.searchPlayers);
		playerRecyclerView = getView().findViewById(R.id.playerRecycleView);
		doneButton = getView().findViewById(R.id.selectPlayersDoneButton);
		loadingIndicator = getView().findViewById(R.id.spLoadingInd);
		loadingText = getView().findViewById(R.id.spTextLoading);

	}

	/**
	 * This is called when the async data from the database has been downloaded. Until then the the functional
	 * views of the program are disabled and a loading symbol is shown
	 */
	@Override
	public void enableViewInteraction() {
		doneButton.setEnabled(true);
		loadingIndicator.setVisibility(View.INVISIBLE);
		playerRecyclerView.setVisibility(View.VISIBLE);


		playerRecyclerView.requestLayout();
		loadingText.setVisibility(View.INVISIBLE);
	}

	@Override
	public void disableViewInteraction() {

	}

	/**
	 * This shows a loading error text if the data couldn't be downloaded.
	 */
	@Override
	public void displayLoadingFailed() {
		loadingIndicator.invalidate();
		loadingText.setText("Loading Failed");
	}

}
