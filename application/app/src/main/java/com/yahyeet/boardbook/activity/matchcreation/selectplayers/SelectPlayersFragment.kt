package com.yahyeet.boardbook.activity.matchcreation.selectplayers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

import com.yahyeet.boardbook.R
import com.yahyeet.boardbook.activity.matchcreation.CreateMatchActivity
import com.yahyeet.boardbook.activity.matchcreation.selectgame.ISelectGameFragment
import com.yahyeet.boardbook.presenter.matchcreation.selectplayers.SelectPlayersPresenter

class SelectPlayersFragment : Fragment(), ISelectGameFragment {

    private var selectPlayersPresenter: SelectPlayersPresenter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val cma = activity as CreateMatchActivity?
        selectPlayersPresenter = SelectPlayersPresenter(this, cma!!.presenter)
        return inflater.inflate(R.layout.fragment_select_players, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        enableMatchFeed()
        enableSearchView()
        this.view!!.findViewById<View>(R.id.selectPlayersDoneButton).setOnClickListener { event ->
            selectPlayersPresenter!!.masterPresenter.goToConfigureTeams()
            println(selectPlayersPresenter!!.masterPresenter.cmdh.selectedPlayers.toString())
        }
    }

    fun enableMatchFeed() {
        val playerRecycler = view!!.findViewById<RecyclerView>(R.id.playerRecycleView)
        playerRecycler.setHasFixedSize(true)
        selectPlayersPresenter!!.enableGameFeed(playerRecycler, view!!.context)
    }

    fun enableSearchView() {
        val searchView = view!!.findViewById<SearchView>(R.id.searchPlayers)
        selectPlayersPresenter!!.enableSearchBar(searchView)
    }
}
