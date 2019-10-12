package com.yahyeet.boardbook.activity;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.presenter.ProfilePresenter;

public class ProfileActivity extends AppCompatActivity implements IProfileActivity {

	private ProfilePresenter profilePresenter;
	private TextView tvUsername;
	private TextView tvWinrate;
	private TextView tvGamesPlayed;
	private ProgressBar pbWinrate;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		profilePresenter = new ProfilePresenter(this);
		enableMatchFeed();

		//TODO: SET THIS IN ANOTHER WAY CAUSE NULL POINTER
		tvUsername = findViewById(R.id.tvUsername);
		tvWinrate = findViewById(R.id.tvWinrate);
		tvGamesPlayed = findViewById(R.id.tvGamesPlayed);
		pbWinrate = findViewById(R.id.pbWinrate);

		setDataFields();
	}

	private void setDataFields() {
		tvUsername.setText(profilePresenter.getLoggedInUserName());
		tvWinrate.setText(profilePresenter.getWinratePercentageForLoggedInUser());
		tvGamesPlayed.setText(profilePresenter.getGamesPlayedForLoggedInUser());
		pbWinrate.setProgress((int) Math.round(profilePresenter.getWinrateQuotaForLoggedInUser() * 100));
	}

	/**
	 * Initiates recyclerView of matches in activity and populates it
	 */
	public void enableMatchFeed() {
		// TODO: Examine how these method calls can get nullPointerException
		RecyclerView matchRecycler = findViewById(R.id.rvProfile);

		// use this setting to improve performance if you know that changes
		// in content do not change the layout size of the RecyclerView
		matchRecycler.setHasFixedSize(true);
		profilePresenter.enableMatchFeed(matchRecycler, getBaseContext());
	}
}
