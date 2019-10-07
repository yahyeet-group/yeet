package com.yahyeet.boardbook.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.presenter.FriendsPresenter;

public class FriendsFragment extends Fragment implements IFriendFragment{

    FriendsPresenter friendsPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        friendsPresenter = new FriendsPresenter(this);
        return inflater.inflate(R.layout.fragment_friends, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        enableFriendList();
    }

    @Override
    public void enableFriendList() {
        RecyclerView recyclerView = getView().findViewById(R.id.friendsRecycler);
        friendsPresenter.enableFriendsList(recyclerView,getContext());
    }
}
