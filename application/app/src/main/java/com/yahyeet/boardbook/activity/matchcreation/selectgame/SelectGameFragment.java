package com.yahyeet.boardbook.activity.matchcreation.selectgame;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.activity.IFutureInteractable;
import com.yahyeet.boardbook.activity.matchcreation.CreateMatchActivity;
import com.yahyeet.boardbook.presenter.matchcreation.selectgame.SelectGamePresenter;

/**
 * @Author Nox/Aaron Sandgren
 * This is the fragment for the step of selecting the game in the Wizard.
 * This instantiates the the SelectGamePresenter for the class and
 * starts up the RecycleView
 */
public class SelectGameFragment extends Fragment implements ISelectGameFragment, IFutureInteractable {

	private SelectGamePresenter selectGamePresenter;
	private RecyclerView gamesRecycleView;
	private ProgressBar loadingIndicator;
	private TextView loadingText;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		CreateMatchActivity cma = (CreateMatchActivity) getActivity();
		selectGamePresenter = new SelectGamePresenter(this, cma.getPresenter());
		return inflater.inflate(R.layout.fragment_select_game, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		gamesRecycleView = view.findViewById(R.id.gamesRecycleView);
		loadingIndicator = view.findViewById(R.id.sgLoadingInd);
		loadingText = view.findViewById(R.id.sgLoadingText);
		enableMatchFeed();

	}


	public void enableMatchFeed() {
		gamesRecycleView.setHasFixedSize(true);
		selectGamePresenter.enableGameFeed(gamesRecycleView, getView().getContext());
	}

	/**
	 * This is called when the async data from the database has been downloaded. Until then the the functional
	 * views of the program are disabled and a loading symbol is shown
	 */
	@Override
	public void enableViewInteraction() {
		loadingIndicator.setVisibility(loadingIndicator.INVISIBLE);
		loadingText.setVisibility(loadingText.INVISIBLE);
		gamesRecycleView.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
		gamesRecycleView.requestLayout();
	}

	@Override
	public void disableViewInteraction() {
	}

	@Override
	public void displayLoadingFailed() {
		loadingText.setText("Loading Failed");
	}
}
