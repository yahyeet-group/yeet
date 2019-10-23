package com.yahyeet.boardbook.presenter.friends.addfriends

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.yahyeet.boardbook.R
import com.yahyeet.boardbook.activity.home.friends.IAddFriendActivity
import com.yahyeet.boardbook.model.entity.User
import com.yahyeet.boardbook.presenter.SearchAdapter
import com.yahyeet.boardbook.presenter.BoardbookSingleton

import java.util.ArrayList

class AddFriendsAdapter(database: List<User>, private val parent: IAddFriendActivity) : SearchAdapter<User>(database) {

    internal class AddFriendsViewHolder(v: View) : RecyclerView.ViewHolder(v) {


        // TODO Replace this area with match class as a custom view object
        private val userName: TextView
        private val userPicture: ImageView
        private val btnAddFriend: ImageButton


        init {
            userName = v.findViewById(R.id.userName)
            userPicture = v.findViewById(R.id.userPicture)
            btnAddFriend = v.findViewById(R.id.addFriendButton)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddFriendsViewHolder {
        val v = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.element_addfriend, parent, false)

        return AddFriendsViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val users = database

        if (holder is AddFriendsViewHolder) {

            holder.userName.text = users!![position].name

            holder.btnAddFriend.setOnClickListener { view ->
                val loggedIn = BoardbookSingleton.getInstance().authHandler.loggedInUser
                loggedIn!!.addFriend(users[position])
                BoardbookSingleton.getInstance().userHandler.save(loggedIn).thenAccept { nothing -> parent.finishAddFriendActivity() }
            }
        }


    }


    override fun createNewFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): Filter.FilterResults {
                val filteredList = ArrayList<User>()
                if (constraint == null || constraint.length == 0) {
                    filteredList.addAll(allEntities)
                } else {
                    val filterPattern = constraint.toString().toLowerCase().trim({ it <= ' ' })

                    for (user in allEntities) {
                        if (user.name!!.toLowerCase().contains(filterPattern)) {
                            filteredList.add(user)
                        }

                    }
                }

                val results = Filter.FilterResults()
                results.values = filteredList

                return results
            }

            override fun publishResults(constraint: CharSequence, results: Filter.FilterResults) {
                database!!.clear()
                database!!.addAll(results.values as List<*>)
                notifyDataSetChanged()
            }
        }
    }

}
