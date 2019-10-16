package com.yahyeet.boardbook.activity.GameActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.presenter.GameDetailPresenter;

public class GameDetailActivity extends AppCompatActivity implements IGameDetailActivity {


	private GameDetailPresenter gameDetailPresenter;
	private TextView tvGameName;
	private TextView tvGameDescription;
	private TextView tvGameRules;
	private ImageView gameImage;
	private RecyclerView teamRecyclerView;

	/**
	 * Binds xml items and creates new presenter
	 *
	 * @param savedInstanceState sent to super
	 */
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

		tvGameName = findViewById(R.id.gameDetailName);
		tvGameDescription = findViewById(R.id.gameDetailDescription);
		tvGameRules = findViewById(R.id.gameDetailRules);
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

	public void setTvGameName(String name) {
		this.tvGameName.setText(name);
	}

	public void setTvGameDescription(String description) {
		this.tvGameDescription.setText(description);
	}

	public void setTvGameRules(String rules) {
		this.tvGameRules.setText(rules);
	}


}
