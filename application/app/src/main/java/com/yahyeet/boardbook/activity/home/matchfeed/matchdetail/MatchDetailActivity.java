package com.yahyeet.boardbook.activity.home.matchfeed.matchdetail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.presenter.matchfeed.matchdetail.MatchDetailPresenter;

public class MatchDetailActivity extends AppCompatActivity implements IMatchDetailActivity {

	private MatchDetailPresenter matchDetailPresenter;


	// TODO: Add more info to page from game/match. Date, etc

	private TextView tvName;
	private RecyclerView rvMatchPlayers;
	private ProgressBar pbLoading;


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
		pbLoading = findViewById(R.id.matchDetailLoading);

		matchDetailPresenter = new MatchDetailPresenter(this, matchID);
	}

	@Override
	public void setGameName(String name) {
		tvName.setText(name);
	}

	// Weird that presenter tells activity to send its information back to presenter
	// Neccessary because activity cant send info before presenter has its match from database
	@Override
	public void initiateMatchDetailList(){
		matchDetailPresenter.enableMatchplayerAdapter(rvMatchPlayers, this, getResources());
	}

	@Override
	public void enableLoading(){
		pbLoading.setVisibility(View.VISIBLE);
	}

	@Override
	public void disableLoading(){
		pbLoading.setVisibility(View.INVISIBLE);
	}






}
