package com.yahyeet.boardbook.activity.home.matchfeed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

import com.yahyeet.boardbook.R
import com.yahyeet.boardbook.presenter.matchfeed.MatchfeedPresenter

class MatchfeedFragment : Fragment(), IMatchfeedFragment {

    private var matchfeedPresenter: MatchfeedPresenter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        matchfeedPresenter = MatchfeedPresenter(this)
        return inflater.inflate(R.layout.fragment_matchfeed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        enableMatchFeed()
    }


    override fun enableMatchFeed() {
        // TODO: Uppdate to current implementation
        val matchRecycler = view!!.findViewById<RecyclerView>(R.id.homeMatchRecycler)

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        matchRecycler.setHasFixedSize(true)
        matchfeedPresenter!!.enableMatchFeed(matchRecycler, view!!.context)
    }


    override fun repopulateMatchFeed() {
        matchfeedPresenter!!.updateMatchAdapter()
    }
}
