package com.yahyeet.boardbook.presenter.friends

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.yahyeet.boardbook.R
import com.yahyeet.boardbook.activity.profile.ProfileActivity
import com.yahyeet.boardbook.model.entity.User
import com.yahyeet.boardbook.presenter.SearchAdapter

import java.util.ArrayList

class FriendsAdapter(database: List<User>, private val context: Context) : SearchAdapter<User>(database) {

    internal class FriendViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        // TODO Replace this area with match class as a custom view object
        private val friendName: TextView
        private val friendPicture: ImageView


        init {
            // Define click listener for the ViewHolder's View.


            friendName = v.findViewById(R.id.friendName)
            friendPicture = v.findViewById(R.id.friendPicture)
        }
    }

    // Creates new view, does not assign data
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val v = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.element_friends, parent, false)

        return FriendViewHolder(v)
    }

    // Replace the contents of a view (invoked by the layout manager)
    // Method that assigns data to the view
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is FriendViewHolder) {
            holder.friendName.text = database!![position].name
            //holder.friendPicture.setImageURI();

            holder.itemView.setOnClickListener { v1 ->
                val startProfile = Intent(context, ProfileActivity::class.java)
                startProfile.putExtra("UserId", database!![position].id)
                context.startActivity(startProfile)
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

    companion object {

        private val TAG = "FriendsAdapter"
    }

}
