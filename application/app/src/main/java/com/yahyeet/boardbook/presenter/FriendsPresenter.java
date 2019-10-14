package com.yahyeet.boardbook.presenter;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.activity.FriendsActivity.IFriendFragment;
import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.presenter.adapter.FriendsAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FriendsPresenter {

    private FriendsAdapter friendsAdapter;
    // TODO: Remove if never necessary
    private IFriendFragment friendsFragment;

    final private List<User> userDatabase = new ArrayList<>();
    private List<User> all = new ArrayList<>();

    public FriendsPresenter(IFriendFragment friendsFragment) {
        this.friendsFragment = friendsFragment;
    }

    /**
     * Makes recyclerView to repopulate its matches with current data
     */
    public void repopulateFriends() {
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

        /*
        for (int i = 0; i < 30; i++) {
            userDatabase.add(new User(Integer.toString(i), "Name: " + i));

        }

        all.addAll(userDatabase);
        */
        initiateFriendPresenter();

        friendsAdapter = new FriendsAdapter(userDatabase);
        matchRecyclerView.setAdapter(friendsAdapter);
    }

    private void initiateFriendPresenter() {

        friendsFragment.disableFragmentInteraction();
        List<User> friends = BoardbookSingleton.getInstance().getAuthHandler().getLoggedInUser().getFriends();


        if(friends != null){
            userDatabase.addAll(friends);
            all.addAll(friends);
        }

        friendsFragment.enableFragmentInteraction();
    }

    public void searchFriends(String query) {
        List<User> temp = findMatchingName(all, query);
        userDatabase.clear();
        userDatabase.addAll(temp);
        repopulateFriends();

    }

    private List<User> findMatchingName(List<User> users, String query) {

        if (query == null)
            return users;

        if (users == null) {
            return new ArrayList<>();
        }

        return users.stream().filter(user -> user.getName().toLowerCase().contains(query)).collect(Collectors.toList());
    }


}