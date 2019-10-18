package com.yahyeet.boardbook.activity.homeActivity.matchfeedFragment.matchDetailActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.presenter.MatchDetailPresenter;

public class MatchDetailActivity extends AppCompatActivity implements IMatchDetailActivity{

	private MatchDetailPresenter matchDetailPresenter;


	// TODO: Add more info to page from game/match. Date, etc

	private TextView tvName;
	private RecyclerView rvMatchPlayers;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_match_detail);


		String matchID;
		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();
			if (extras == null) {
				matchID = null;
			} else {
				matchID = extras.getString("Match");
			}
		} else {
			matchID = (String) savedInstanceState.getSerializable("Match");
		}

		tvName = findViewById(R.id.tvMatchDetailName);
		rvMatchPlayers = findViewById(R.id.rvMatchDetail);

		matchDetailPresenter = new MatchDetailPresenter(this, matchID);
	}

	@Override
	public void setGameName(String name) {
		tvName.setText(name);
	}

	/**
	 * Initiates activity and enables team list
	 */
	protected void onStart() {
		super.onStart();
		matchDetailPresenter.initiateGameDetail();
		matchDetailPresenter.enableMatchplayerAdapter(rvMatchPlayers, this, getResources());
	}
}
