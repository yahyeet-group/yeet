package com.yahyeet.boardbook.activity.matchcreation.selectgame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

import com.yahyeet.boardbook.R
import com.yahyeet.boardbook.activity.matchcreation.CreateMatchActivity
import com.yahyeet.boardbook.presenter.matchcreation.selectgame.SelectGamePresenter

class SelectGameFragment : Fragment(), ISelectGameFragment {

    private var selectGamePresenter: SelectGamePresenter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val cma = activity as CreateMatchActivity?
        selectGamePresenter = SelectGamePresenter(this, cma!!.presenter)
        return inflater.inflate(R.layout.fragment_select_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        enableMatchFeed()
    }

    fun enableMatchFeed() {
        // TODO: Examine how these method calls can get nullPointerException
        val gameRecycler = view!!.findViewById<RecyclerView>(R.id.gamesRecycleView)

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        gameRecycler.setHasFixedSize(true)
        selectGamePresenter!!.enableGameFeed(gameRecycler, view!!.context)
    }

}
