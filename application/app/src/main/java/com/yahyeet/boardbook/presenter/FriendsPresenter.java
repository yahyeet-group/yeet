package com.yahyeet.boardbook.presenter;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.activity.IFriendFragment;
import com.yahyeet.boardbook.activity.IHomeFragment;
import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.presenter.adapter.FriendsAdapter;
import com.yahyeet.boardbook.presenter.adapter.MatchAdapter;

import java.util.ArrayList;
import java.util.List;

public class FriendsPresenter {

    private FriendsAdapter friendsAdapter;
    // TODO: Remove if never necessary
    private IFriendFragment friendsFragment;

    public FriendsPresenter(IFriendFragment friendsFragment) { this.friendsFragment = friendsFragment; }

    /**
     * Makes recyclerView to repopulate its matches with current data
     */
    public void repopulateMatches() {
        friendsAdapter.notifyDataSetChanged();
    }

    /**
     * Creates the necessary structure for populating matches
     *
     * @param matchRecyclerView the RecyclerView that will be populated with matches
     */
    public void enableFriendsList(RecyclerView matchRecyclerView, Context viewContext) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(viewContext);
        matchRecyclerView.setLayoutManager(layoutManager);

        List<User> userList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            userList.add(new User(Integer.toString(i), "Name: " + i));

        }



        friendsAdapter = new FriendsAdapter(userList);
        matchRecyclerView.setAdapter(friendsAdapter);
    }

}