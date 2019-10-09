package com.yahyeet.boardbook.activity.AddFriends;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.activity.AddFriends.AddFriendActivity;
import com.yahyeet.boardbook.activity.IFriendFragment;
import com.yahyeet.boardbook.presenter.FriendsPresenter;

public class FriendsFragment extends Fragment implements IFriendFragment {

    FriendsPresenter friendsPresenter;
    ImageButton addFriendbtn;

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

        View view = getView();

        addFriendbtn = view.findViewById(R.id.addFriendButton);

        addFriendbtn.setOnClickListener(view1 -> {
            Intent intent = new Intent(getContext(), AddFriendActivity.class);
            startActivity(intent);
        });


    }

    @Override
    public void enableFriendList() {
        RecyclerView recyclerView = getView().findViewById(R.id.friendsRecycler);
        friendsPresenter.enableFriendsList(recyclerView, getContext());
    }
}
