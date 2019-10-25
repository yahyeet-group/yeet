package com.yahyeet.boardbook.activity.profile;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.presenter.ProfilePresenter;

/**
 * Activity that displays a profile
 */
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

		String userId;
		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();
			if(extras == null) {
				userId = null;
			} else {
				userId = extras.getString("UserId");
			}
		} else {
			// TODO: Maybe bad, idk // Vex
			userId = (String) savedInstanceState.getSerializable("UserId");
		}

		profilePresenter = new ProfilePresenter(this, userId);
		enableMatchFeed();
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
