package com.yahyeet.boardbook.activity.home.matchfeed.matchdetail

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView

import com.yahyeet.boardbook.R
import com.yahyeet.boardbook.activity.IFutureInteractable
import com.yahyeet.boardbook.presenter.matchfeed.matchdetail.MatchDetailPresenter

class MatchDetailActivity : AppCompatActivity(), IMatchDetailActivity, IFutureInteractable {

    private var matchDetailPresenter: MatchDetailPresenter? = null


    // TODO: Add more info to page from game/match. Date, etc

    private var tvName: TextView? = null
    private var rvMatchPlayers: RecyclerView? = null
    private var pbLoading: ProgressBar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_detail)


        val matchID: String?
        if (savedInstanceState == null) {
            val extras = intent.extras
            if (extras == null) {
                matchID = null
            } else {
                matchID = extras.getString("Match")
            }
        } else {
            matchID = savedInstanceState.getSerializable("Match") as String
        }

        tvName = findViewById(R.id.tvMatchDetailName)
        rvMatchPlayers = findViewById(R.id.rvMatchDetail)
        pbLoading = findViewById(R.id.matchDetailLoading)

        matchDetailPresenter = matchID?.let { MatchDetailPresenter(this, it) }
    }

    override fun setGameName(name: String) {
        tvName!!.text = name
    }

    // Necessary because activity cant send info before presenter has its match from database
    override fun initiateMatchDetailList() {
        matchDetailPresenter!!.enableMatchplayerAdapter(rvMatchPlayers!!, this, resources)
    }

    override fun disableViewInteraction() {
        pbLoading!!.visibility = View.VISIBLE
    }


    override fun enableViewInteraction() {
        pbLoading!!.visibility = View.INVISIBLE
    }


    override fun displayLoadingFailed() {
        //TODO: Implement method
    }


}
