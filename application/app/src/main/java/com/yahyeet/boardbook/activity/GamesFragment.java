package com.yahyeet.boardbook.activity;

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

public class GamesFragment extends Fragment implements IGameFragment{

    private GamePresenter gamePresenter;
    private TextView searchInput;
    private ListView gameListView;
    private GridView gameGridView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        gamePresenter = new GamePresenter(this);
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    @Override
    public void onViewCreated(@Nonnull View view, Bundle savedInstanceState) {

        Button enableList = getView().findViewById(R.id.listDisplayButton);
        Button enableGrid = getView().findViewById(R.id.gridDisplayButton);

        // TODO: Figure out why it wants view1 and view2 as parameters
        enableList.setOnClickListener(view1 -> enableGameList());

        enableGrid.setOnClickListener(view2 -> enableGameGrid());

        // TODO: Examine how these method calls can get nullPointerException
        gameListView = getView().findViewById(R.id.gameListView);
        gameGridView = getView().findViewById(R.id.gameGridView);
        searchInput = getView().findViewById(R.id.searchInput);

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

    private DisplayMetrics getScreenMetrics(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        // TODO: Make sure activity is not null, fragment exists without activity?
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    public void enableGameList(){
        gamePresenter.displayGameList(getView().getContext(), gameListView);
        gameListView.setVisibility(View.VISIBLE);
        gameGridView.setVisibility(View.INVISIBLE);
    }

    public void enableGameGrid(){
        gamePresenter.displayGameGrid(getView().getContext(), gameGridView);
        gameGridView.setVisibility(View.VISIBLE);
        gameListView.setVisibility(View.INVISIBLE);
    }



}
