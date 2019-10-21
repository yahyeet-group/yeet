package com.yahyeet.boardbook.activity.home.game;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.activity.IFutureInteractable;
import com.yahyeet.boardbook.presenter.game.GamePresenter;

import javax.annotation.Nonnull;

public class GamesFragment extends Fragment implements IGameFragment, IFutureInteractable {

	private GamePresenter gamePresenter;
	private TextView tvSearch;
	private TextView tvLoadError;
	private RecyclerView rvGame;
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

		gamePresenter = new GamePresenter(this, getView().getContext());

		btnEnableList.setOnClickListener(view1 -> enableGameList());
		btnEnableGrid.setOnClickListener(view2 -> enableGameGrid());
		tvSearch.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void afterTextChanged(Editable editable) {
				gamePresenter.searchGames(tvSearch.getText().toString());
			}
		});

		gamePresenter.bindAdapterToView(rvGame);
	}

	/**
	 * Binds the IDs of XML items to references in class
	 */
	private void setAllViews() {
		View view = getView();

		if (view != null) {
			rvGame = view.findViewById(R.id.gameRecyclerView);
			tvSearch = view.findViewById(R.id.searchInput);
			tvLoadError = view.findViewById(R.id.gameErrorText);

			btnEnableList = view.findViewById(R.id.gameListDisplayButton);
			btnEnableGrid = view.findViewById(R.id.gameGridDisplayButton);
		}
	}


	/**
	 * Displays and enables games to show as a list
	 */
	private void enableGameList() {
		if (getView() != null) {
			gamePresenter.displayGameList(rvGame);
		}
	}

	/**
	 * Displays and enables games to show as a grid
	 */
	private void enableGameGrid() {
		if (getView() != null) {
			gamePresenter.displayGameGrid(rvGame);
		}
	}

	@Override
	public void enableViewInteraction() {

		tvSearch.setEnabled(true);
		btnEnableList.setEnabled(true);
		btnEnableGrid.setEnabled(true);
		View view = getView();
		if (view != null)
			view.findViewById(R.id.gameLoadingLayout).setVisibility(View.INVISIBLE);

	}

	@Override
	public void disableViewInteraction() {

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
