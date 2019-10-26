package com.yahyeet.boardbook.activity.home.game.gamedetail;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.activity.IFutureInteractable;
import com.yahyeet.boardbook.presenter.game.gamedetail.GameDetailPresenter;

/**
 * Activity that shows more detailed information about a match
 */
public class GameDetailActivity extends AppCompatActivity implements IGameDetailActivity, IFutureInteractable {


	private GameDetailPresenter gameDetailPresenter;
	private TextView gameName;
	private TextView gameDescription;
	private ProgressBar gameDetailLoading;
	private RecyclerView teamRecyclerView;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_detail);
		setAllIds();

		String gameID;
		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();
			if (extras == null) {
				gameID = null;
			} else {
				gameID = extras.getString("Game");
			}
		} else {
			gameID = (String) savedInstanceState.getSerializable("Game");
		}

		gameDetailPresenter = new GameDetailPresenter(this, gameID);


	}

	@Override
	public void enableRecyclerView(){
		gameDetailPresenter.enableTeamList(teamRecyclerView, this);
	}

	public void setGameName(String name) {
		this.gameName.setText(name);
	}

	public void setGameDescription(String description) {
		this.gameDescription.setText(description);
	}

	@Override
	public void enableViewInteraction() {
		gameName.setVisibility(View.VISIBLE);
		gameDescription.setVisibility(View.VISIBLE);

		gameDetailLoading.setVisibility(View.INVISIBLE);

	}

	@Override
	public void disableViewInteraction() {
		gameName.setVisibility(View.INVISIBLE);
		gameDescription.setVisibility(View.INVISIBLE);

		gameDetailLoading.setVisibility(View.VISIBLE);

	}

	@Override
	public void displayLoadingFailed() {
		findViewById(R.id.gameDetailError).setVisibility(View.VISIBLE);
	}

	private void setAllIds(){
		gameName = findViewById(R.id.gameDetailName);
		gameDescription = findViewById(R.id.gameDetailDescription);
		gameDetailLoading = findViewById(R.id.gameDetailLoading);
		teamRecyclerView = findViewById(R.id.gameDetailRecyclerView);
	}
}
