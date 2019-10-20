package com.yahyeet.boardbook.activity.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;


import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.activity.home.friends.FriendsFragment;
import com.yahyeet.boardbook.activity.home.game.GamesFragment;
import com.yahyeet.boardbook.activity.home.matchfeed.MatchfeedFragment;
import com.yahyeet.boardbook.presenter.HomePresenter;

public class HomeActivity extends AppCompatActivity implements IHomeActivity {


	private HomePresenter homePresenter;

	private BottomNavigationView.OnNavigationItemSelectedListener navListener = menuItem -> {
		Fragment selectedFragment = null;

		switch (menuItem.getItemId()) {
			case R.id.nav_home:
				selectedFragment = new MatchfeedFragment();
				break;
			case R.id.nav_game:
				selectedFragment = new GamesFragment();
				break;
			case R.id.nav_friends:
				selectedFragment = new FriendsFragment();
				break;
			case R.id.nav_chat:
				selectedFragment = new ChatFragment();
				break;
		}
		getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
		return true;
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		homePresenter = new HomePresenter(this);

		BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
		bottomNav.setOnNavigationItemSelectedListener(navListener);

		getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MatchfeedFragment()).commit();

		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.toolbar_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case R.id.profile:
				homePresenter.startLoggedInUserProfile();
				return true;
			case R.id.logoff:
				homePresenter.logOut();
				finish();
				return true;
			default :
				return super.onOptionsItemSelected(item);
		}

	}
}
