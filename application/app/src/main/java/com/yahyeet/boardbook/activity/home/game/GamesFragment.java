package com.yahyeet.boardbook.activity.home.game;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.presenter.GamePresenter;

import javax.annotation.Nonnull;

public class GamesFragment extends Fragment implements IGameFragment {

	private GamePresenter gamePresenter;
	private TextView tvSearch;
	private TextView tvLoadError;
	private ListView gameListView;
	private GridView gameGridView;
	private Button btnEnableList;
	private Button btnEnableGrid;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		return inflater.inflate(R.layout.fragment_game, container, false);
	}

	@Override
	public void onViewCreated(@Nonnull View view, Bundle savedInstanceState) {
		setAllViews();

		gamePresenter = new GamePresenter(this);

		btnEnableList.setOnClickListener(view1 -> enableGameList());
		btnEnableGrid.setOnClickListener(view2 -> enableGameGrid());
		tvSearch.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				gamePresenter.searchGames(tvSearch.getText().toString());
			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void afterTextChanged(Editable editable) {
				gamePresenter.searchGames(tvSearch.getText().toString());
			}
		});

		gameGridView.setColumnWidth(getScreenWidth() / 3);

		enableGameList();
	}

	/**
	 * Binds the IDs of XML items to references in class
	 */
	private void setAllViews() {
		View view = getView();

		if (view != null) {
			gameListView = view.findViewById(R.id.gameListView);
			gameGridView = view.findViewById(R.id.gameGridView);
			tvSearch = view.findViewById(R.id.searchInput);
			tvLoadError = view.findViewById(R.id.gameErrorText);

			btnEnableList = view.findViewById(R.id.gameListDisplayButton);
			btnEnableGrid = view.findViewById(R.id.gameGridDisplayButton);
		}
	}

	/**
	 * Finds the width of the screen
	 * @return width of screen
	 */
	private int getScreenWidth() {
		DisplayMetrics displayMetrics = new DisplayMetrics();
		// TODO: Make sure activity is not null, fragment exists without activity?
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		return displayMetrics.widthPixels;
	}

	/**
	 * Displays and enables games to show as a list
	 */
	private void enableGameList() {
		if (getView() != null) {
			gamePresenter.displayGameList(getView().getContext(), gameListView);
			gameListView.setVisibility(View.VISIBLE);
			gameGridView.setVisibility(View.INVISIBLE);
		}
	}

	/**
	 * Displays and enables games to show as a grid
	 */
	private void enableGameGrid() {
		if (getView() != null) {
			gamePresenter.displayGameGrid(getView().getContext(), gameGridView);
			gameGridView.setVisibility(View.VISIBLE);
			gameListView.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	public void enableFragmentInteraction() {

		tvSearch.setEnabled(true);
		btnEnableList.setEnabled(true);
		btnEnableGrid.setEnabled(true);
		View view = getView();
		if (view != null)
			view.findViewById(R.id.gameLoadingLayout).setVisibility(View.INVISIBLE);

	}

	@Override
	public void disableFragmentInteraction() {

		tvSearch.setEnabled(false);
		btnEnableList.setEnabled(false);
		btnEnableGrid.setEnabled(false);
		View view = getView();
		if (view != null)
			view.findViewById(R.id.gameLoadingLayout).setVisibility(View.VISIBLE);
	}

	@Override
	public void displayLoadingFailed(){
		tvLoadError.setVisibility(View.VISIBLE);
	}
}