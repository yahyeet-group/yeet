package com.yahyeet.boardbook.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;


import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.activity.FriendsActivity.FriendsFragment;
import com.yahyeet.boardbook.activity.GameActivity.GamesFragment;

public class HomeActivity extends AppCompatActivity {


	private BottomNavigationView.OnNavigationItemSelectedListener navListener = menuItem -> {
		Fragment selectedFragment = null;

		switch (menuItem.getItemId()) {
			case R.id.nav_home:
				selectedFragment = new HomeFragment();
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

		BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
		bottomNav.setOnNavigationItemSelectedListener(navListener);

		getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

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
				Intent startCurrentUserProfile = new Intent();
				return true;
			case R.id.logoff:

				return true;
			default :
				return super.onOptionsItemSelected(item);
		}

	}
}
