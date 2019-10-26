package com.yahyeet.boardbook.activity.home.matchfeed;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.activity.IFutureInteractable;
import com.yahyeet.boardbook.presenter.matchfeed.MatchfeedPresenter;

import javax.annotation.Nonnull;

/**
 * Fragment that displays a feed of matches
 */
public class MatchfeedFragment extends Fragment implements IMatchfeedFragment, IFutureInteractable {


	private MatchfeedPresenter matchfeedPresenter;
	private RecyclerView rvMatch;

	private View parentView;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_matchfeed, container, false);
	}

	@Override
	public void onViewCreated(@Nonnull View view, Bundle savedInstanceState) {
		parentView = getView();
		rvMatch = getView().findViewById(R.id.homeMatchRecycler);

		matchfeedPresenter = new MatchfeedPresenter(this);
	}

	@Override
	public void bindAdapterToView() {
		if(getView() != null){
			RecyclerView matchRecycler = getView().findViewById(R.id.homeMatchRecycler);

			// use this setting to improve performance if you know that changes
			// in content do not change the layout size of the RecyclerView
			matchRecycler.setHasFixedSize(true);
			matchfeedPresenter.enableMatchFeed(matchRecycler, getView().getContext());
		}

	}

	@Override
	public void enableViewInteraction() {
		rvMatch.setEnabled(true);
		if(parentView != null)
			parentView.findViewById(R.id.matchfeedProgressLoading).setVisibility(View.INVISIBLE);
	}

	@Override
	public void disableViewInteraction() {
		rvMatch.setEnabled(false);
		if(parentView != null)
			parentView.findViewById(R.id.matchfeedProgressLoading).setVisibility(View.VISIBLE);
	}

	@Override
	public void displayLoadingFailed() {
		if(parentView != null)
			parentView.findViewById(R.id.matchfeedError).setVisibility(View.VISIBLE);
	}

	@Override
	public void onStart() {
		super.onStart();
		matchfeedPresenter.updateMatchDatabase();
	}

}
