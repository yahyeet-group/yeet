package com.yahyeet.boardbook.activity.home.game.gamedetail

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

import com.yahyeet.boardbook.R
import com.yahyeet.boardbook.presenter.game.gamedetail.GameDetailPresenter

class GameDetailActivity : AppCompatActivity(), IGameDetailActivity {


    private var gameDetailPresenter: GameDetailPresenter? = null
    private var gameName: TextView? = null
    private var gameDescription: TextView? = null
    private var gameRules: TextView? = null
    private val gameImage: ImageView? = null
    private var teamRecyclerView: RecyclerView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_detail)

        val gameID: String?
        if (savedInstanceState == null) {
            val extras = intent.extras
            if (extras == null) {
                gameID = null
            } else {
                gameID = extras.getString("Game")
            }
        } else {
            gameID = savedInstanceState.getSerializable("Game") as String
        }

        gameDetailPresenter = gameID?.let { GameDetailPresenter(this, it) }

        gameName = findViewById(R.id.gameDetailName)
        gameDescription = findViewById(R.id.gameDetailDescription)
        gameRules = findViewById(R.id.gameDetailRules)
        teamRecyclerView = findViewById(R.id.gameDetailRecyclerView)
    }

    /**
     * Initiates activity and enables team list
     */
    override fun onStart() {
        super.onStart()
        gameDetailPresenter!!.initiateGameDetail()
        gameDetailPresenter!!.enableTeamList(teamRecyclerView!!, this)
    }

    override fun setGameName(name: String) {
        this.gameName!!.text = name
    }

    override fun setGameDescription(description: String) {
        this.gameDescription!!.text = description
    }

    override fun setGameRules(rules: String) {
        this.gameRules!!.text = rules
    }


}
