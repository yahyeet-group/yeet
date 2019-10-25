package com.yahyeet.boardbook.activity.matchcreation;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.activity.matchcreation.configureteams.ConfigureTeamsFragment;
import com.yahyeet.boardbook.activity.matchcreation.selectgame.SelectGameFragment;
import com.yahyeet.boardbook.activity.matchcreation.selectplayers.SelectPlayersFragment;
import com.yahyeet.boardbook.presenter.matchcreation.CMMasterPresenter;

/**
 * Activity that hold all fragments for creating a match
 */
public class CreateMatchActivity extends AppCompatActivity implements ICreateMatchActivity {

 private CMMasterPresenter presenter;
 private ConfigureTeamsFragment configureTeamsFragment;
 private SelectGameFragment selectGameFragment;
 private SelectPlayersFragment selectPlayersFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_match);
		presenter = new CMMasterPresenter(this);

		configureTeamsFragment = new ConfigureTeamsFragment();
		selectPlayersFragment = new SelectPlayersFragment();
		selectGameFragment = new SelectGameFragment();

		getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SelectGameFragment()).commit();
	}



	public CMMasterPresenter getPresenter(){
		return presenter;
	}

	@Override
	public void onBackPressed() {
		if (configureTeamsFragment.equals(getSupportFragmentManager().findFragmentByTag("Teams")))
			goToSelectPlayers();
		else if (selectPlayersFragment.equals(getSupportFragmentManager().findFragmentByTag("Players")))
			goToSelectGame();
		else
			super.onBackPressed();
	}

	@Override
	public void goToConfigureTeams() {
		getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, configureTeamsFragment, "Teams").commit();
	}

	@Override
	public void goToSelectPlayers() {
		getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectPlayersFragment, "Players").commit();
	}

	@Override
	public void finalizeMatchCreation() {
		finish();
	}

	@Override
	public void goToSelectGame() {
		getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectGameFragment, "Game").commit();
	}


}
