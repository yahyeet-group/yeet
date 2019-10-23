package com.yahyeet.boardbook.activity.home.game

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

import com.yahyeet.boardbook.R
import com.yahyeet.boardbook.activity.IFutureInteractable
import com.yahyeet.boardbook.presenter.game.GamePresenter

class GamesFragment : Fragment(), IGameFragment, IFutureInteractable {

    private var gamePresenter: GamePresenter? = null
    private var tvSearch: TextView? = null
    private var tvLoadError: TextView? = null
    private var rvGame: RecyclerView? = null
    private var btnEnableList: Button? = null
    private var btnEnableGrid: Button? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setAllViews()

        gamePresenter = GamePresenter(this, getView()!!.context)

        btnEnableList!!.setOnClickListener { view1 -> enableGameList() }
        btnEnableGrid!!.setOnClickListener { view2 -> enableGameGrid() }
        tvSearch!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun afterTextChanged(editable: Editable) {
                gamePresenter!!.searchGames(tvSearch!!.text.toString())
            }
        })

        gamePresenter!!.bindAdapterToView(rvGame!!)
    }

    /**
     * Binds the IDs of XML items to references in class
     */
    private fun setAllViews() {
        val view = view

        if (view != null) {
            rvGame = view.findViewById(R.id.gameRecyclerView)
            tvSearch = view.findViewById(R.id.searchInput)
            tvLoadError = view.findViewById(R.id.gameErrorText)

            btnEnableList = view.findViewById(R.id.gameListDisplayButton)
            btnEnableGrid = view.findViewById(R.id.gameGridDisplayButton)
        }
    }


    /**
     * Displays and enables games to show as a list
     */
    private fun enableGameList() {
        if (view != null) {
            gamePresenter!!.displayGameList(rvGame!!)
        }
    }

    /**
     * Displays and enables games to show as a grid
     */
    private fun enableGameGrid() {
        if (view != null) {
            gamePresenter!!.displayGameGrid(rvGame!!)
        }
    }

    override fun enableViewInteraction() {

        tvSearch!!.isEnabled = true
        btnEnableList!!.isEnabled = true
        btnEnableGrid!!.isEnabled = true
        val view = view
        if (view != null)
            view.findViewById<View>(R.id.gameLoadingLayout).visibility = View.INVISIBLE

    }

    override fun disableViewInteraction() {

        tvSearch!!.isEnabled = false
        btnEnableList!!.isEnabled = false
        btnEnableGrid!!.isEnabled = false
        val view = view
        if (view != null)
            view.findViewById<View>(R.id.gameLoadingLayout).visibility = View.VISIBLE
    }

    override fun displayLoadingFailed() {
        tvLoadError!!.visibility = View.VISIBLE
    }
}
