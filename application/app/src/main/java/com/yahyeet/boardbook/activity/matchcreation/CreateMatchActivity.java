package com.yahyeet.boardbook.activity.matchcreation;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.activity.matchcreation.configureteams.ConfigureTeamsFragment;
import com.yahyeet.boardbook.activity.matchcreation.selectgame.SelectGameFragment;
import com.yahyeet.boardbook.activity.matchcreation.selectplayers.SelectPlayersFragment;
import com.yahyeet.boardbook.presenter.matchcreation.CMMasterPresenter;

/**
 * This is the main activity to the whole match logging wizard.
 * This instantiates on the clicking of the "plus button" in the main ui
 * where the class controls the switching of steps and handles the
 * passing of the main presenter
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

	/**
	 * See return
	 * @return The master presenter for the whole activity
	 */
	public CMMasterPresenter getPresenter() {
		return presenter;
	}


	/**
	 * Comfigures so that the back button in the standard android uino longer exists the entire match wizard but backs a wizard page
	 */
	@Override
	public void onBackPressed() {
		if (configureTeamsFragment.equals(getSupportFragmentManager().findFragmentByTag("Teams")))
			goToSelectPlayers();
		else if (selectPlayersFragment.equals(getSupportFragmentManager().findFragmentByTag("Players")))
			goToSelectGame();
		else
			super.onBackPressed();
	}

	/**
	 * Method to go to the configure teams part of the wizard
	 */
	@Override
	public void goToConfigureTeams() {
		getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, configureTeamsFragment, "Teams").commit();
	}

	/**
	 * Method to go to the selecting players part of the wizard
	 */
	@Override
	public void goToSelectPlayers() {
		getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectPlayersFragment, "Players").commit();
	}

	/**
	 * When the user is done logging a new match the whole wizard closes together with the activity sending the user to the location from before
	 */
	@Override
	public void finalizeMatchCreation() {
		finish();
	}


	/**
	 * Method to go to the selecting game part of the wizard
	 */
	@Override
	public void goToSelectGame() {
		getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectGameFragment, "Game").commit();
	}


}
