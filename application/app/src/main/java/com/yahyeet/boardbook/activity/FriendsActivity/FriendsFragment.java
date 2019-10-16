package com.yahyeet.boardbook.activity.FriendsActivity;

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
import com.yahyeet.boardbook.presenter.FriendsPresenter;

import javax.annotation.Nonnull;

public class FriendsFragment extends Fragment implements IFriendFragment {

	private FriendsPresenter friendsPresenter;
	private TextView tvFriendSearch;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		friendsPresenter = new FriendsPresenter(this);
		return inflater.inflate(R.layout.fragment_friends, container, false);
	}

	@Override
	public void onViewCreated(@Nonnull View view, Bundle savedInstanceState) {
		setAllViews();

		friendsPresenter = new FriendsPresenter(this);

		tvFriendSearch.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				friendsPresenter.searchFriends(tvFriendSearch.getText().toString());
			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


			}

			@Override
			public void afterTextChanged(Editable editable) {
				friendsPresenter.searchFriends(tvFriendSearch.getText().toString());
			}
		});

	}

	private void setAllViews() {

		tvFriendSearch = getView().findViewById((R.id.friendSearch));

	}

	public void onStart() {
		super.onStart();
		enableFriendList();

		View view = getView();

		ImageButton addFriendbtn = view.findViewById(R.id.addFriendButton);

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

	@Override
	public void disableFragmentInteraction() {
		//addFriendbtn.setEnabled(false);
		tvFriendSearch.setEnabled(false);
	}

	@Override
	public void enableFragmentInteraction() {

		//addFriendbtn.setEnabled(true);
		tvFriendSearch.setEnabled(true);
	}
}
