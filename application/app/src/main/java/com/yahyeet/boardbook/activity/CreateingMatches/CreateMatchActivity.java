package com.yahyeet.boardbook.activity.CreateingMatches;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.activity.CreateingMatches.ConfigureTeams.ConfigureTeamsFragment;
import com.yahyeet.boardbook.activity.CreateingMatches.SelectGame.SelectGameFragment;

public class CreateMatchActivity extends AppCompatActivity implements ICreateMatchActivity {

 private CMMasterPresenter presenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_match);
		presenter = new CMMasterPresenter(this);

		getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SelectGameFragment()).commit();
	}

	public CMMasterPresenter getPresenter(){
		return presenter;
	}


}
