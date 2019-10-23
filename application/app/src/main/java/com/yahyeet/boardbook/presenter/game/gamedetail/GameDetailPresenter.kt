package com.yahyeet.boardbook.presenter.game.gamedetail

import android.content.Context

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.yahyeet.boardbook.activity.home.game.gamedetail.IGameDetailActivity
import com.yahyeet.boardbook.model.entity.Game
import com.yahyeet.boardbook.model.entity.GameRole
import com.yahyeet.boardbook.model.entity.GameTeam
import com.yahyeet.boardbook.presenter.BoardbookSingleton

import java.util.ArrayList
import java.util.concurrent.ExecutionException

class GameDetailPresenter(private val gameDetailActivity: IGameDetailActivity, gameID: String) {
    private var teamAdapter: GameDetailAdapter? = null
    private var game: Game? = null

    init {
        try {
            game = BoardbookSingleton.getInstance().gameHandler.find(gameID).get()
        } catch (e: ExecutionException) {
            // TODO: What to do here?
        } catch (e: InterruptedException) {
        }

    }

    fun initiateGameDetail() {
        gameDetailActivity.setGameName(game!!.name)
        gameDetailActivity.setGameDescription(game!!.description)
        // TODO: Add rules to games, String field
        gameDetailActivity.setGameRules("")
    }

    /**
     * Makes recyclerView to repopulate its matches with current data
     */
    fun updateTeamAdapter() {
        teamAdapter!!.notifyDataSetChanged()
    }

    /**
     * Creates the necessary structure for populating teams
     *
     * @param teamRecyclerView the RecyclerView that will be populated with teams
     */
    fun enableTeamList(teamRecyclerView: RecyclerView, viewContext: Context) {

        val layoutManager = LinearLayoutManager(viewContext)
        teamRecyclerView.layoutManager = layoutManager

        val allTeamRoleLists = ArrayList<List<GameRole>>()

        for (team in game!!.teams) {
            allTeamRoleLists.add(team.roles)
        }

        teamAdapter = GameDetailAdapter(game!!.teams, allTeamRoleLists)
        teamRecyclerView.adapter = teamAdapter
    }
}
