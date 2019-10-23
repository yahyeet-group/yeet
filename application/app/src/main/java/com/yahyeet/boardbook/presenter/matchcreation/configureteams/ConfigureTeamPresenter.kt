package com.yahyeet.boardbook.presenter.matchcreation.configureteams

import android.content.Context
import android.widget.Spinner

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.yahyeet.boardbook.activity.matchcreation.configureteams.ConfigureTeamsFragment
import com.yahyeet.boardbook.activity.matchcreation.configureteams.IConfigureTeamsFragment
import com.yahyeet.boardbook.model.entity.MatchPlayer
import com.yahyeet.boardbook.model.entity.User
import com.yahyeet.boardbook.presenter.matchcreation.CMMasterPresenter
import com.yahyeet.boardbook.presenter.matchcreation.selectplayers.PlayerAdapter

import java.util.ArrayList

class ConfigureTeamPresenter(ctf: ConfigureTeamsFragment, val masterPresenter: CMMasterPresenter) {

    private var configureTeamAdapter: ConfigureTeamAdapter? = null

    private var playerRecycleView: RecyclerView? = null

    fun repopulateMatches() {
        configureTeamAdapter!!.notifyDataSetChanged()
    }

    fun enableGameFeed(playerRecycleView: RecyclerView, viewContext: Context) {
        this.playerRecycleView = playerRecycleView
        val layoutManager = LinearLayoutManager(viewContext)
        playerRecycleView.layoutManager = layoutManager
        //TODO: Replace with matches from user
        val testSet = arrayOf(User(), User())
        configureTeamAdapter = ConfigureTeamAdapter(this)
        playerRecycleView.adapter = configureTeamAdapter


    }

    fun finalizeMatch() {
        val players = ArrayList<MatchPlayer>()
        for (i in 0 until playerRecycleView!!.adapter!!.itemCount) {

            val holder = playerRecycleView!!.findViewHolderForAdapterPosition(i) as ConfigureTeamAdapter.PlayerViewHolder?
            players.add(holder!!.matchPlayer)
        }

        masterPresenter.cmdh.addPlayer(players)
        masterPresenter.finalizeMatch()

    }

}
