package com.yahyeet.boardbook.activity.home.matchfeed.matchdetail;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.activity.IFutureInteractable;
import com.yahyeet.boardbook.presenter.matchfeed.matchdetail.MatchDetailPresenter;

/**
 * Activity that shows detail for a match
 */
public class MatchDetailActivity extends AppCompatActivity implements IMatchDetailActivity, IFutureInteractable {

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
		initiateMatchDetailList();
	}

	public void initiateMatchDetailList(){
		matchDetailPresenter.enableMatchplayerAdapter(rvMatchPlayers, this);
	}

	@Override
	public void setGameName(String name) {
		tvName.setText(name);
	}



	@Override
	public void disableViewInteraction(){
		pbLoading.setVisibility(View.VISIBLE);
	}



	@Override
	public void enableViewInteraction(){
		pbLoading.setVisibility(View.INVISIBLE);
	}


	@Override
	public void displayLoadingFailed() {
		findViewById(R.id.matchDetailError).setVisibility(View.VISIBLE);
	}



}
