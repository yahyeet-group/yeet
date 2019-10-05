package com.yahyeet.boardbook.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.presenter.GameListPresenter;

import javax.annotation.Nonnull;

public class GamesFragment extends Fragment implements IGameFragment{

    private GameListPresenter gameListPresenter;
    private TextView searchInput;
    private ListView gameListView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        gameListPresenter = new GameListPresenter(this);
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    @Override
    public void onViewCreated(@Nonnull View view, Bundle savedInstanceState) {
        enableGames();
        gameListView = getView().findViewById(R.id.gameListView);
        searchInput = getView().findViewById(R.id.searchInput);
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                gameListPresenter.updateGamesWithQuery(searchInput.getText().toString());
            }
        });
    }

    private void enableGames(){
        // TODO: Examine how these method calls can get nullPointerException
        ListView gameListView = getView().findViewById(R.id.gameListView);
        gameListPresenter.enableGameList(getView().getContext(), gameListView);
    }



}
