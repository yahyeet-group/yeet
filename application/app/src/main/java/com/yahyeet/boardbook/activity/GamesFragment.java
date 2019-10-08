package com.yahyeet.boardbook.activity;

import android.content.Context;
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
    private TextView searchInput;
    private ListView gameListView;
    private GridView gameGridView;
    private Button enableList;
    private Button enableGrid;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    @Override
    public void onViewCreated(@Nonnull View view, Bundle savedInstanceState) {
        setAllViews();

        gamePresenter = new GamePresenter(this);

        enableList.setOnClickListener(view1 -> enableGameList());
        enableGrid.setOnClickListener(view2 -> enableGameGrid());
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                gamePresenter.updateGamesWithQuery(searchInput.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        int width = getScreenMetrics().widthPixels;
        gameGridView.setColumnWidth(width / 3);


        enableGameGrid();
    }

    private void setAllViews(){

        // TODO: Examine how these method calls can get nullPointerException
        gameListView = getView().findViewById(R.id.gameListView);
        gameGridView = getView().findViewById(R.id.gameGridView);
        searchInput = getView().findViewById(R.id.searchInput);

        enableList = getView().findViewById(R.id.gameListDisplayButton);
        enableGrid = getView().findViewById(R.id.gameGridDisplayButton);
    }

    private DisplayMetrics getScreenMetrics() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        // TODO: Make sure activity is not null, fragment exists without activity?
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    private void enableGameList() {
        gamePresenter.displayGameList(getView().getContext(), gameListView);
        gameListView.setVisibility(View.VISIBLE);
        gameGridView.setVisibility(View.INVISIBLE);
    }

    private void enableGameGrid() {
        gamePresenter.displayGameGrid(getView().getContext(), gameGridView);
        gameGridView.setVisibility(View.VISIBLE);
        gameListView.setVisibility(View.INVISIBLE);
    }


    @Override
    public void disableFragmentInteraction() {

        searchInput.setEnabled(false);
        enableList.setEnabled(false);
        enableGrid.setEnabled(false);
        getView().findViewById(R.id.gameLoadingLayout).setVisibility(View.VISIBLE);


    }

    @Override
    public void enableFragmentInteraction() {

        searchInput.setEnabled(true);
        enableList.setEnabled(true);
        enableGrid.setEnabled(true);
        getView().findViewById(R.id.gameLoadingLayout).setVisibility(View.INVISIBLE);

    }
}
