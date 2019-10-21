package com.yahyeet.boardbook.activity.home.friends;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.presenter.friends.addfriends.AddFriendPresenter;

public class AddFriendActivity extends AppCompatActivity implements IAddFriendActivity {

	private AddFriendPresenter addFriendPresenter;
	private EditText tvAddFriendSearch;
	private TextView tvError;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addfriend);

		tvError = findViewById(R.id.addFriendError);
		tvAddFriendSearch = findViewById(R.id.addFriendSearch);


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

			}

			@Override
			public void afterTextChanged(Editable editable) {
				addFriendPresenter.searchNonFriends(tvAddFriendSearch.getText().toString());
			}
		});
	}

	@Override
	public void enableAddFriendList() {
		RecyclerView recyclerView = findViewById(R.id.addFriendsRecycle);
		addFriendPresenter.enableAddFriendsList(recyclerView, this);
	}

	@Override
	public void disableViewInteraction() {
		tvAddFriendSearch.setEnabled(false);
		findViewById(R.id.addFriendLoadlingLayout).setVisibility(View.VISIBLE);

	}

	@Override
	public void enableViewInteraction() {
		tvAddFriendSearch.setEnabled(true);
		findViewById(R.id.addFriendLoadlingLayout).setVisibility(View.INVISIBLE);

	}

	@Override
	public void displayLoadingFailed() {
		tvError.setVisibility(View.VISIBLE);
	}

	@Override
	public void finishAddFriendActivity() {
		finish();
	}
}
