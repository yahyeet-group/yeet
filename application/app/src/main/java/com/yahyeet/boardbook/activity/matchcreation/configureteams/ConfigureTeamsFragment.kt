package com.yahyeet.boardbook.activity.matchcreation.configureteams

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

import com.yahyeet.boardbook.R
import com.yahyeet.boardbook.activity.matchcreation.CreateMatchActivity
import com.yahyeet.boardbook.presenter.matchcreation.configureteams.ConfigureTeamPresenter

class ConfigureTeamsFragment : Fragment(), IConfigureTeamsFragment {

    private var spp: ConfigureTeamPresenter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val cma = activity as CreateMatchActivity?
        spp = cma!!.presenter?.let { ConfigureTeamPresenter(this, it) }
        return inflater.inflate(R.layout.fragment_configure_teams, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        enableMatchFeed()
    }

    fun enableMatchFeed() {
        val gameRecycler = view!!.findViewById<RecyclerView>(R.id.configureTeamsRecyleView)
        gameRecycler.setHasFixedSize(true)
        spp!!.enableGameFeed(gameRecycler, view!!.context)

        view!!.findViewById<View>(R.id.matchDone).setOnClickListener { event -> spp!!.finalizeMatch() }

    }
}