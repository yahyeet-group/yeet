package com.yahyeet.boardbook.activity.CreateingMatches;

import android.app.Activity;

import androidx.fragment.app.FragmentManager;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.activity.CreateingMatches.ConfigureTeams.ConfigureTeamsFragment;
import com.yahyeet.boardbook.activity.CreateingMatches.SelectGame.SelectGameFragment;
import com.yahyeet.boardbook.activity.CreateingMatches.SelectPlayers.SelectPlayersFragment;

public class CMMasterPresenter {

	private CreateMatchDataHandler cmdh;
	private CreateMatchActivity activity;
	private FragmentManager fm;

	public CMMasterPresenter(CreateMatchActivity activity) {
		this.activity = activity;
		cmdh = new CreateMatchDataHandler(activity);
		fm = activity.getSupportFragmentManager();
	}

	public void goToConfigureTeams() {
		fm.beginTransaction().replace(R.id.fragment_container, new ConfigureTeamsFragment()).commit();
	}

	public void goToSelectGame(){
		fm.beginTransaction().replace(R.id.fragment_container, new SelectGameFragment()).commit();
	}

	public void goToSelectPlayers(){
		fm.beginTransaction().replace(R.id.fragment_container, new SelectPlayersFragment()).commit();
	}

	public CreateMatchDataHandler getCmdh(){
		return cmdh;
	}

}
