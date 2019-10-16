package com.yahyeet.boardbook.activity.FriendsActivity;

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
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.presenter.AddFriendPresenter;
import com.yahyeet.boardbook.presenter.FriendsPresenter;

public class AddFriendActivity extends AppCompatActivity implements IAddFriendActivity {

	private AddFriendPresenter addFriendPresenter;
	private ImageButton backbtn;
	private TextView addFriendSearch;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addfriend);


		backbtn = findViewById(R.id.backButton);
		addFriendSearch = findViewById(R.id.addFriendSearch);

		backbtn.setOnClickListener(view1 -> {
			onBackPressed();
		});
	}

	@Override
	protected void onStart() {
		super.onStart();

		addFriendPresenter = new AddFriendPresenter(this);
		enableAddFriendList();

		addFriendSearch.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				addFriendPresenter.searchNonFriends(addFriendSearch.getText().toString());

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
		backbtn.setEnabled(false);
		addFriendSearch.setEnabled(false);
		findViewById(R.id.addFriendLoadlingLayout).setVisibility(View.VISIBLE);

	}

	@Override
	public void enableActivityInteraction() {
		backbtn.setEnabled(true);
		addFriendSearch.setEnabled(true);
		findViewById(R.id.addFriendLoadlingLayout).setVisibility(View.INVISIBLE);

	}
}
