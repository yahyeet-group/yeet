package com.yahyeet.boardbook.presenter.matchfeed

import android.content.Context

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.yahyeet.boardbook.model.entity.User
import com.yahyeet.boardbook.presenter.BoardbookSingleton
import com.yahyeet.boardbook.activity.home.matchfeed.IMatchfeedFragment
import com.yahyeet.boardbook.model.entity.Match
import com.yahyeet.boardbook.model.handler.MatchHandlerListener

import java.util.ArrayList

class MatchfeedPresenter(// TODO: Remove if never necessary
        private val matchfeedFragment: IMatchfeedFragment) : MatchHandlerListener {

    private var matchfeedAdapter: MatchfeedAdapter? = null
    private val matchDatabase = ArrayList<Match>()

    /**
     * Makes recyclerView to repopulate its matches with current data
     */
    fun updateMatchAdapter() {
        matchfeedAdapter!!.notifyDataSetChanged()
    }

    /**
     * Creates the necessary structure for populating matches
     *
     * @param matchRecyclerView the RecyclerView that will be populated with matches
     */
    fun enableMatchFeed(matchRecyclerView: RecyclerView, viewContext: Context) {

        val loggedIn = BoardbookSingleton.getInstance().authHandler.loggedInUser


        matchDatabase.addAll(loggedIn!!.matches)


        // TODO: This code breaks everything and needs to be reimplemented
        // TODO: If implemented then IMatchfeedFragment needs to extend IFutureIntractable
        /*CompletableFuture.allOf(loggedIn
			.getFriends()
			.stream()
			.map(friend -> BoardbookSingleton.getInstance().getUserHandler()
				.find(friend.getId()).thenApply(populatedFriend -> {
					matchDatabase.addAll(populatedFriend.getMatches());
					return null;
				})).toArray(CompletableFuture[]::new)).thenAccept(nothing -> {
			// Now all are added

		});*/
        val layoutManager = LinearLayoutManager(viewContext)
        matchRecyclerView.layoutManager = layoutManager
        matchfeedAdapter = MatchfeedAdapter(viewContext, matchDatabase)
        matchRecyclerView.adapter = matchfeedAdapter


    }

    override fun onAddMatch(match: Match) {
        matchDatabase.add(match)
        updateMatchAdapter()
    }

    override fun onUpdateMatch(match: Match) {
        for (i in matchDatabase.indices) {
            if (matchDatabase[i].id == match.id) {
                matchDatabase[i] = match
            }
        }
        updateMatchAdapter()
    }

    override fun onRemoveMatch(match: Match) {
        for (i in matchDatabase.indices) {
            if (matchDatabase[i].id == match.id) {
                matchDatabase.removeAt(i)
                break
            }
        }
        updateMatchAdapter()
    }
}
