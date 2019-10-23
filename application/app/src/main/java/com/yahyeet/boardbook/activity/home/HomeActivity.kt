package com.yahyeet.boardbook.activity.home

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton

import com.google.android.material.bottomnavigation.BottomNavigationView


import com.yahyeet.boardbook.R
import com.yahyeet.boardbook.activity.home.friends.FriendsFragment
import com.yahyeet.boardbook.activity.home.game.GamesFragment
import com.yahyeet.boardbook.activity.home.matchfeed.MatchfeedFragment
import com.yahyeet.boardbook.activity.matchcreation.CreateMatchActivity
import com.yahyeet.boardbook.presenter.HomePresenter

class HomeActivity : AppCompatActivity(), IHomeActivity {


    private var homePresenter: HomePresenter? = null
    private var btn_create: ImageButton? = null

    private val navListener = { menuItem : MenuItem ->
        var selectedFragment: Fragment? = null

        when (menuItem.getItemId()) {
            R.id.nav_home -> selectedFragment = MatchfeedFragment()
            R.id.nav_game -> selectedFragment = GamesFragment()
            R.id.nav_friends -> selectedFragment = FriendsFragment()
            R.id.nav_chat -> selectedFragment = ChatFragment()
        }
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, selectedFragment!!).commit()
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        btn_create = findViewById(R.id.btn_create)

        btn_create!!.setOnClickListener { v -> startCreateMatch() }

        homePresenter = HomePresenter(this)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener(navListener)

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, MatchfeedFragment()).commit()

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        findViewById<View>(R.id.btn_create).setOnClickListener { event ->
            val intent = Intent(this, CreateMatchActivity::class.java)
            startActivity(intent)
        }
    }


    private fun startCreateMatch() {
        val startCreateMatch = Intent(this, CreateMatchActivity::class.java)
        startActivity(startCreateMatch)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.profile -> {
                homePresenter!!.startLoggedInUserProfile()
                return true
            }
            R.id.logoff -> {
                homePresenter!!.logOut()
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }

    }
}
