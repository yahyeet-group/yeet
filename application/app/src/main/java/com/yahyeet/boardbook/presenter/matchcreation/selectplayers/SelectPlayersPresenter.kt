package com.yahyeet.boardbook.presenter.matchcreation.selectplayers

import android.content.Context
import android.widget.SearchView

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.yahyeet.boardbook.activity.matchcreation.selectplayers.SelectPlayersFragment
import com.yahyeet.boardbook.model.entity.User
import com.yahyeet.boardbook.presenter.matchcreation.CMMasterPresenter

import java.util.ArrayList

class SelectPlayersPresenter(private val spf: SelectPlayersFragment, val masterPresenter: CMMasterPresenter) {
    private var playerAdapter: PlayerAdapter? = null
    private val database = ArrayList<User>()

    fun repopulateMatches() {
        playerAdapter!!.notifyDataSetChanged()
    }

    fun enableGameFeed(gameRecycleView: RecyclerView, viewContext: Context) {
        val layoutManager = LinearLayoutManager(viewContext)
        gameRecycleView.layoutManager = layoutManager
        /*
		List<User> testSet = new LinkedList<>();
		User testUser = new User();
		testUser.setName("Jaan Karm");
		testSet.add(testUser);
		User testUser2 = new User();
		testUser2.setName("Broberg Bror");
		testSet.add(testUser2);
		User testUser3 = new User();
		testUser3.setName("Rolf the Kid");
		testSet.add(testUser3);
		User testUser4 = new User();
		testUser4.setName("Daniel the Man");
		testSet.add(testUser4);*/

        playerAdapter = PlayerAdapter(database, this)
        gameRecycleView.adapter = playerAdapter
    }

    fun enableSearchBar(searchView: SearchView) {

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                playerAdapter!!.filter.filter(newText)
                return false
            }
        })
    }
}
