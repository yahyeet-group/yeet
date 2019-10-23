package com.yahyeet.boardbook.presenter.game

import android.content.Context

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.yahyeet.boardbook.activity.IFutureInteractable
import com.yahyeet.boardbook.activity.home.game.IGameFragment
import com.yahyeet.boardbook.model.entity.Game
import com.yahyeet.boardbook.model.handler.GameHandler
import com.yahyeet.boardbook.model.handler.GameHandlerListener
import com.yahyeet.boardbook.presenter.AdapterPresenter
import com.yahyeet.boardbook.presenter.BoardbookSingleton

class GamePresenter(private val gameFragment: IFutureInteractable, viewContext: Context) : AdapterPresenter<Game, GameHandler>(gameFragment), GameHandlerListener {
    private var listLayoutManager: RecyclerView.LayoutManager? = null
    private var gridLayoutManager: RecyclerView.LayoutManager? = null

    private var currentDisplayType: DisplayType? = null

    init {
        currentDisplayType = DisplayType.LIST


        val gameHandler = BoardbookSingleton.getInstance().gameHandler

        fillDatabase(gameHandler)
        gameHandler.addListener(this)



        setLayoutManagers(viewContext)
        adapter = GameAdapter(database, viewContext, DisplayType.LIST)

    }

    fun bindAdapterToView(recyclerView: RecyclerView) {
        recyclerView.layoutManager = listLayoutManager
        recyclerView.adapter = adapter
    }

    fun displayGameList(recyclerView: RecyclerView) {
        currentDisplayType = DisplayType.LIST

        recyclerView.layoutManager = listLayoutManager
        updateGameAdapter()
        notifyAdapter()
    }

    fun displayGameGrid(recyclerView: RecyclerView) {
        currentDisplayType = DisplayType.GRID

        recyclerView.layoutManager = gridLayoutManager
        updateGameAdapter()
        notifyAdapter()
    }

    private fun setLayoutManagers(viewContext: Context) {
        listLayoutManager = LinearLayoutManager(viewContext)
        gridLayoutManager = GridLayoutManager(viewContext, 3)
    }

    fun searchGames(query: String) {
        adapter!!.filter.filter(query)
    }


    private fun updateGameAdapter() {
        (adapter as GameAdapter).setDisplayType(currentDisplayType)
    }

    private fun notifyAdapter() {
        adapter!!.notifyDataSetChanged()
    }

    override fun onAddGame(game: Game) {
        database.add(game)
        notifyAdapter()
    }

    override fun onUpdateGame(game: Game) {
        for (i in 0 until database.size) {
            if (database[i].id == game.id) {
                database[i] = game
            }
        }
        notifyAdapter()
    }

    override fun onRemoveGame(game: Game) {
        for (i in 0 until database.size) {
            if (database[i].id == game.id) {
                database.removeAt(i)
                break
            }
        }
        notifyAdapter()
    }


}
