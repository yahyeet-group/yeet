package com.yahyeet.boardbook.presenter.friends.addfriends

import android.content.Context
import android.os.Looper

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.yahyeet.boardbook.activity.IFutureInteractable
import com.yahyeet.boardbook.activity.home.friends.IAddFriendActivity
import com.yahyeet.boardbook.model.Boardbook
import com.yahyeet.boardbook.model.handler.UserHandler
import com.yahyeet.boardbook.presenter.AdapterPresenter
import com.yahyeet.boardbook.presenter.BoardbookSingleton
import com.yahyeet.boardbook.model.entity.User

import java.util.ArrayList
import java.util.stream.Collectors

class AddFriendPresenter(private val addFriendActivity: IAddFriendActivity) : AdapterPresenter<User, UserHandler>(addFriendActivity as IFutureInteractable) {

    init {

        fillAndModifyDatabase(BoardbookSingleton.getInstance().userHandler)
    }


    /**
     * Creates the necessary structure for populating matches
     *
     * @param recyclerView the RecyclerView that will be populated with matches
     */
    fun enableAddFriendsList(recyclerView: RecyclerView, viewContext: Context) {
        recyclerView.layoutManager = LinearLayoutManager(viewContext)

        adapter = AddFriendsAdapter(database, addFriendActivity)
        recyclerView.adapter = adapter
    }

    override fun modifyDatabase(database: MutableList<User>?) {
        val myFriends = BoardbookSingleton.getInstance().authHandler.loggedInUser!!.friends
        if (database != null && myFriends != null) {

            database.remove(BoardbookSingleton.getInstance().authHandler.loggedInUser)

            val notMyFriends = database
                    .stream()
                    .filter { user -> myFriends.stream().noneMatch { friend -> friend.id == user.id } }
                    .collect<List<User>, Any>(Collectors.toList())

            setDatabase(notMyFriends)
        }
    }

    fun searchNonFriends(query: String) {
        adapter!!.filter.filter(query)

    }

}