package com.yahyeet.boardbook.activity.matchcreation

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity

import com.yahyeet.boardbook.R
import com.yahyeet.boardbook.activity.matchcreation.configureteams.ConfigureTeamsFragment
import com.yahyeet.boardbook.activity.matchcreation.selectgame.SelectGameFragment
import com.yahyeet.boardbook.activity.matchcreation.selectplayers.SelectPlayersFragment
import com.yahyeet.boardbook.presenter.matchcreation.CMMasterPresenter

class CreateMatchActivity : AppCompatActivity(), ICreateMatchActivity {

    var presenter: CMMasterPresenter? = null
        private set
    private var configureTeamsFragment: ConfigureTeamsFragment? = null
    private var selectGameFragment: SelectGameFragment? = null
    private var selectPlayersFragment: SelectPlayersFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_match)
        presenter = CMMasterPresenter(this)

        configureTeamsFragment = ConfigureTeamsFragment()
        selectPlayersFragment = SelectPlayersFragment()
        selectGameFragment = SelectGameFragment()

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, SelectGameFragment()).commit()
    }

    override fun onBackPressed() {
        if (configureTeamsFragment == supportFragmentManager.findFragmentByTag("Teams"))
            goToSelectPlayers()
        else if (selectPlayersFragment == supportFragmentManager.findFragmentByTag("Players"))
            goToSelectGame()
        else
            super.onBackPressed()
    }

    override fun goToConfigureTeams() {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, configureTeamsFragment!!, "Teams").commit()
    }

    override fun goToSelectPlayers() {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, selectPlayersFragment!!, "Players").commit()
    }

    override fun finalizeMatchCreation() {
        finish()
    }

    override fun goToSelectGame() {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, selectGameFragment!!, "Game").commit()
    }


}
