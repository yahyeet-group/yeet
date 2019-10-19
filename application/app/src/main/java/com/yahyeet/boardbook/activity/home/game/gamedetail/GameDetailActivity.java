package com.yahyeet.boardbook.activity.home.game.gamedetail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.presenter.game.gamedetail.GameDetailPresenter;

public class GameDetailActivity extends AppCompatActivity implements IGameDetailActivity {


	private GameDetailPresenter gameDetailPresenter;
	private TextView gameName;
	private TextView gameDescription;
	private TextView gameRules;
	private ImageView gameImage;
	private RecyclerView teamRecyclerView;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_detail);

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

		gameName = findViewById(R.id.gameDetailName);
		gameDescription = findViewById(R.id.gameDetailDescription);
		gameRules = findViewById(R.id.gameDetailRules);
		teamRecyclerView = findViewById(R.id.gameDetailRecyclerView);
	}

	/**
	 * Initiates activity and enables team list
	 */
	protected void onStart() {
		super.onStart();
		gameDetailPresenter.initiateGameDetail();
		gameDetailPresenter.enableTeamList(teamRecyclerView, this);
	}

	public void setGameName(String name) {
		this.gameName.setText(name);
	}

	public void setGameDescription(String description) {
		this.gameDescription.setText(description);
	}

	public void setGameRules(String rules) {
		this.gameRules.setText(rules);
	}


}
