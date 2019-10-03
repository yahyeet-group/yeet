package com.yahyeet.boardbook.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;


import android.content.Intent;

import com.yahyeet.boardbook.R;

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
    }


    /**
     * This method when called starts a new login activity
     *
     * @param view is the visual object (ex a button) the method is bound to (I think)
     */
    public void changeToLogin(View view) {
        // TODO: Change so that old activities are retained and not created anew
        Intent intent = new Intent(this, LoginFragment.class);
        startActivity(intent);
    }
}
