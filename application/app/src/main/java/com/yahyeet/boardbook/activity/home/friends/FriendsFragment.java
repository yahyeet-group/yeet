package com.yahyeet.boardbook.activity.home.friends;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.activity.IFutureInteractable;
import com.yahyeet.boardbook.presenter.friends.FriendsPresenter;

import javax.annotation.Nonnull;

/**
 * Fragment that holds and displays friends that is opened by bottom bar
 */
public class FriendsFragment extends Fragment implements IFriendFragment, IFutureInteractable {

	private FriendsPresenter friendsPresenter;
	private ImageButton addFriendbtn;
	private TextView friendSearch;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_friends, container, false);
	}

	@Override
	public void onViewCreated(@Nonnull View view, Bundle savedInstanceState) {
		setAllViews();
		friendsPresenter = new FriendsPresenter(this);
		friendSearch.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


			}

			@Override
			public void afterTextChanged(Editable editable) {
				friendsPresenter.searchFriends(friendSearch.getText().toString());
			}
		});


		enableFriendList();
	}

	private void setAllViews() {

		View view = getView();

		friendSearch = view.findViewById((R.id.friendSearch));
		addFriendbtn = view.findViewById(R.id.addFriendButton);

		addFriendbtn.setOnClickListener(view1 -> {
			Intent intent = new Intent(getContext(), AddFriendActivity.class);
			startActivity(intent);
		});

	}

	public void onStart() {
		super.onStart();
		friendsPresenter.updateFriends();
	}

	@Override
	public void enableFriendList() {
		RecyclerView recyclerView = getView().findViewById(R.id.friendsRecycler);
		friendsPresenter.enableFriendsList(recyclerView, getContext());
	}

	@Override
	public void enableViewInteraction() {
		addFriendbtn.setEnabled(true);
		View view = getView();
		if(view != null)
			view.findViewById(R.id.friendsLoading).setVisibility(View.INVISIBLE);
	}

	@Override
	public void disableViewInteraction() {
		addFriendbtn.setEnabled(true);
		View view = getView();
		if(view != null)
			view.findViewById(R.id.friendsLoading).setVisibility(View.VISIBLE);

	}

	@Override
	public void displayLoadingFailed() {
		View view = getView();
		if(view != null)
			view.findViewById(R.id.friendsError).setVisibility(View.VISIBLE);
	}
}
