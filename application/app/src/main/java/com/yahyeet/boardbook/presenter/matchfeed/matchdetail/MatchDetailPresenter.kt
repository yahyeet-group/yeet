package com.yahyeet.boardbook.presenter.matchfeed.matchdetail

import android.content.Context
import android.content.res.Resources
import android.os.Looper

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.yahyeet.boardbook.activity.IFutureInteractable
import com.yahyeet.boardbook.activity.home.matchfeed.matchdetail.IMatchDetailActivity
import com.yahyeet.boardbook.presenter.BoardbookSingleton
import com.yahyeet.boardbook.model.entity.Match


class MatchDetailPresenter(private val matchDetailActivity: IMatchDetailActivity, matchID: String) {
    private var matchPlayerAdapter: MatchPlayerAdapter? = null
    private var match: Match? = null

    init {


        if (matchDetailActivity is IFutureInteractable) {

            val futureDetail = matchDetailActivity as IFutureInteractable

            futureDetail.disableViewInteraction()
            BoardbookSingleton.getInstance().matchHandler.find(matchID).thenAccept { foundMatch ->
                match = foundMatch

                android.os.Handler(Looper.getMainLooper()).post {
                    setMatchDetailName()
                    matchDetailActivity.initiateMatchDetailList()
                    futureDetail.enableViewInteraction()
                }

            }.exceptionally { e ->
                e.printStackTrace()
                futureDetail.displayLoadingFailed()
                null
            }
        } else {
            throw IllegalArgumentException("Activity not instance of IFutureIntractable")
        }

    }


    private fun setMatchDetailName() {
        matchDetailActivity.setGameName("Game of " + match!!.game!!.name!!)
    }


    /**
     * Makes recyclerView to repopulate its matches with current data
     */
    fun updateMatchplayerAdapter() {
        matchPlayerAdapter!!.notifyDataSetChanged()
    }

    /**
     * Initiates the adapter for a matchplayerRecyclerView
     *
     * @param matchplayerRecyclerView view that will receive new adapter
     * @param viewContext             structure of current view
     */
    fun enableMatchplayerAdapter(matchplayerRecyclerView: RecyclerView, viewContext: Context, resources: Resources) {

        val layoutManager = GridLayoutManager(viewContext, 1)

        matchplayerRecyclerView.layoutManager = layoutManager

        matchPlayerAdapter = MatchPlayerAdapter(match!!.matchPlayers, resources)
        matchplayerRecyclerView.adapter = matchPlayerAdapter
    }


}
