package com.yahyeet.boardbook.presenter.friends

import android.content.Context

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.yahyeet.boardbook.activity.home.friends.IFriendFragment
import com.yahyeet.boardbook.model.entity.User
import com.yahyeet.boardbook.model.handler.UserHandlerListener
import com.yahyeet.boardbook.model.repository.IRepositoryListener
import com.yahyeet.boardbook.presenter.BoardbookSingleton
import com.yahyeet.boardbook.presenter.friends.FriendsAdapter

import java.util.ArrayList
import java.util.stream.Collectors

class FriendsPresenter(// TODO: Remove if never necessary
        private val friendsFragment: IFriendFragment) : UserHandlerListener {

    private var friendsAdapter: FriendsAdapter? = null

    private val userDatabase: MutableList<User>

    init {

        userDatabase = BoardbookSingleton.getInstance().authHandler.loggedInUser!!.friends
        BoardbookSingleton.getInstance().userHandler.addListener(this)
    }

    /**
     * Makes recyclerView to repopulate its users with current data
     */
    fun notifyAdapter() {
        friendsAdapter!!.notifyDataSetChanged()
    }

    /**
     * Creates the necessary structure for populating matches
     *
     * @param friendsRecyclerView the RecyclerView that will be populated with matches
     */
    fun enableFriendsList(friendsRecyclerView: RecyclerView, viewContext: Context) {
        val layoutManager = LinearLayoutManager(viewContext)
        friendsRecyclerView.layoutManager = layoutManager

        friendsAdapter = FriendsAdapter(userDatabase, viewContext)
        friendsRecyclerView.adapter = friendsAdapter

    }

    fun searchFriends(query: String) {
        friendsAdapter!!.filter.filter(query)
    }

    override fun onAddUser(user: User) {
        // New users don't automatically become friends
        notifyAdapter()
    }

    override fun onUpdateUser(user: User) {
        for (i in userDatabase.indices) {
            if (userDatabase[i].id == user.id) {
                userDatabase[i] = user
            }
        }
        notifyAdapter()
    }

    override fun onRemoveUser(user: User) {
        for (i in userDatabase.indices) {
            if (userDatabase[i].id == user.id) {
                userDatabase.removeAt(i)
                break
            }
        }
        notifyAdapter()
    }
}