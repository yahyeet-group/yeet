package com.yahyeet.boardbook.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.presenter.GameListPresenter;

import javax.annotation.Nonnull;

public class GamesFragment extends Fragment implements IGameFragment{

    GameListPresenter gameListPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        gameListPresenter = new GameListPresenter(this);
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    @Override
    public void onViewCreated(@Nonnull View view, Bundle savedInstanceState) {
        enableGames();
    }

    private void enableGames(){
        // TODO: Examine how these method calls can get nullPointerException
        ListView gameListView = getView().findViewById(R.id.gameListView);

        gameListPresenter.enableGameList(getView().getContext(), gameListView);
    }



}
