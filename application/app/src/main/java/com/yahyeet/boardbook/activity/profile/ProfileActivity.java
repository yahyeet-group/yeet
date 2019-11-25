package com.yahyeet.boardbook.activity.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.activity.IFutureInteractable;
import com.yahyeet.boardbook.presenter.ProfilePresenter;

/**
 * Activity that displays a profile
 */
public class ProfileActivity extends AppCompatActivity implements IProfileActivity, IFutureInteractable {


	private ProfilePresenter profilePresenter;
	private RelativeLayout pbLoading;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);

		pbLoading = findViewById(R.id.profileLoading);

		String userId;
		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();
			if(extras == null) {
				userId = null;
			} else {
				userId = extras.getString("UserId");
			}
		} else {
			userId = (String) savedInstanceState.getSerializable("UserId");
		}

		profilePresenter = new ProfilePresenter(this, userId);
	}

	@Override
	public void enableViewInteraction() {
		pbLoading.setVisibility(View.INVISIBLE);
	}

	@Override
	public void disableViewInteraction() {
		pbLoading.setVisibility(View.VISIBLE);

	}

	@Override
	public void displayLoadingFailed() {
		findViewById(R.id.profileError).setVisibility(View.VISIBLE);
	}

	@Override
	public void enableAdapter() {
		RecyclerView matchRecycler = findViewById(R.id.rvProfile);

		// use this setting to improve performance if you know that changes
		// in content do not change the layout size of the RecyclerView
		matchRecycler.setHasFixedSize(true);
		profilePresenter.enableMatchFeed(matchRecycler, this);
	}
}
