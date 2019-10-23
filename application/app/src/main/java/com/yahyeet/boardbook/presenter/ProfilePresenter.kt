package com.yahyeet.boardbook.presenter

import android.content.Context

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.yahyeet.boardbook.model.handler.GameHandler
import com.yahyeet.boardbook.model.handler.MatchHandler
import com.yahyeet.boardbook.presenter.matchfeed.MatchfeedAdapter
import com.yahyeet.boardbook.activity.profile.IProfileActivity
import com.yahyeet.boardbook.model.entity.Match
import com.yahyeet.boardbook.model.entity.User

import java.util.ArrayList
import java.util.concurrent.ExecutionException

class ProfilePresenter(private val profileActivity: IProfileActivity, userId: String) {

    private var matchfeedAdapter: MatchfeedAdapter? = null
    private val matchDatabase = ArrayList<Match>()
    private var user: User? = null

    init {
        try {
            user = BoardbookSingleton.getInstance().userHandler.find(userId).get()
            matchDatabase.addAll(user!!.matches)
        } catch (e: ExecutionException) {
            // TODO: What to do here?
        } catch (e: InterruptedException) {
        }

    }

    /**
     * Makes recyclerView to repopulate its matches with current data
     */
    fun updateMatchAdapter() {
        matchfeedAdapter!!.notifyDataSetChanged()
    }

    /**
     * Creates the necessary structure for populating matches
     *
     * @param matchRecyclerView the RecyclerView that will be populated with matches
     */
    fun enableMatchFeed(matchRecyclerView: RecyclerView, viewContext: Context) {

        val layoutManager = LinearLayoutManager(viewContext)
        matchRecyclerView.layoutManager = layoutManager
        matchfeedAdapter = MatchfeedAdapter(viewContext, matchDatabase, user, BoardbookSingleton.getInstance().statisticsUtil)
        matchRecyclerView.adapter = matchfeedAdapter
    }

}
