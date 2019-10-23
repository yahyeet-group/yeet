package com.yahyeet.boardbook.presenter.matchcreation.selectgame

import android.content.Context

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.yahyeet.boardbook.model.entity.Game
import com.yahyeet.boardbook.presenter.BoardbookSingleton
import com.yahyeet.boardbook.presenter.matchcreation.CMMasterPresenter
import com.yahyeet.boardbook.activity.matchcreation.selectgame.ISelectGameFragment

import java.util.ArrayList

class SelectGamePresenter(private val selectGameFragment: ISelectGameFragment, val masterPresenter: CMMasterPresenter) {

    private var gamesAdapter: GamesAdapter? = null
    private val dataset = ArrayList<Game>()

    fun updateAdapter() {
        gamesAdapter!!.notifyDataSetChanged()
    }

    fun enableGameFeed(gameRecycleView: RecyclerView, viewContext: Context) {
        val layoutManager = LinearLayoutManager(viewContext)
        gameRecycleView.layoutManager = layoutManager
        /*Game testGame = new Game("Avalon", "Cool Game", 3, 5, 10);
		GameTeam mom = new GameTeam();
		mom.setName("Minions of Mordred");
		GameRole mordred = new GameRole();
		mordred.setName("Mordred");
		GameRole minion = new GameRole();
		minion.setName("Minion of Mordred");
		mom.addRole(mordred);
		mom.addRole(minion);
		testGame.addTeam(mom);
		GameTeam som = new GameTeam();
		som.setName("Servants of Merlin");
		GameRole merlin = new GameRole();
		merlin.setName("Merlin");
		GameRole servant = new GameRole();
		servant.setName("Servant of Merlin");
		som.addRole(merlin);
		som.addRole(servant);
		testGame.addTeam(som);


		List<Game> testSet = new ArrayList<>();
		testSet.add(testGame);*/



        gamesAdapter = GamesAdapter(dataset, this)
        gameRecycleView.adapter = gamesAdapter
    }

}
