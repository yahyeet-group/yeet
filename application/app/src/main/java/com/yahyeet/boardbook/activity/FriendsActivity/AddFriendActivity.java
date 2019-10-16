package com.yahyeet.boardbook.activity.FriendsActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.presenter.AddFriendPresenter;

public class AddFriendActivity extends AppCompatActivity implements IAddFriendActivity {

	private AddFriendPresenter addFriendPresenter;
	private ImageButton btnBack;
	private TextView tvAddFriendSearch;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addfriend);


		btnBack = findViewById(R.id.backButton);
		tvAddFriendSearch = findViewById(R.id.addFriendSearch);

		btnBack.setOnClickListener(view1 -> {
			onBackPressed();
		});
	}

	@Override
	protected void onStart() {
		super.onStart();

		addFriendPresenter = new AddFriendPresenter(this);
		enableAddFriendList();

		tvAddFriendSearch.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				addFriendPresenter.searchNonFriends(tvAddFriendSearch.getText().toString());

			}

			@Override
			public void afterTextChanged(Editable editable) {

			}
		});
	}

	@Override
	public void enableAddFriendList() {
		RecyclerView recyclerView = findViewById(R.id.addFriendsRecycle);
		addFriendPresenter.enableAddFriendsList(recyclerView, this);
	}

	@Override
	public void addFriend() {

	}

	@Override
	public void disableActivityInteraction() {
		btnBack.setEnabled(false);
		tvAddFriendSearch.setEnabled(false);
		findViewById(R.id.addFriendLoadlingLayout).setVisibility(View.VISIBLE);

	}

	@Override
	public void enableActivityInteraction() {
		btnBack.setEnabled(true);
		tvAddFriendSearch.setEnabled(true);
		findViewById(R.id.addFriendLoadlingLayout).setVisibility(View.INVISIBLE);

	}
}
