package com.yahyeet.boardbook.presenter.matchfeed

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.yahyeet.boardbook.R
import com.yahyeet.boardbook.activity.home.matchfeed.matchdetail.MatchDetailActivity
import com.yahyeet.boardbook.model.entity.Match
import com.yahyeet.boardbook.model.entity.MatchPlayer
import com.yahyeet.boardbook.model.entity.User
import com.yahyeet.boardbook.model.util.StatisticsUtil
import com.yahyeet.boardbook.presenter.BoardbookSingleton

import java.util.ArrayList


class MatchfeedAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private var context: Context? = null
    private var matches: List<Match>? = null

    //TODO: Naming might need to change
    private val user: User?
    private val statisticsUtil: StatisticsUtil


    constructor(context: Context, dataset: List<Match>?) {
        if (dataset != null)
            matches = dataset
        else
            matches = ArrayList()

        this.context = context
    }

    constructor(context: Context, dataset: List<Match>?, user: User, statisticsUtil: StatisticsUtil) {
        if (dataset != null)
            matches = dataset
        else
            matches = ArrayList()

        this.context = context
        this.user = user
        this.statisticsUtil = statisticsUtil
    }


    inner class HeaderViewHolder internal constructor(v: View) : RecyclerView.ViewHolder(v) {
        private val tvUsername: TextView
        private val tvWinrate: TextView
        private val tvGamesPlayed: TextView
        private val pbWinrate: ProgressBar

        init {
            //Does nothing when clicked on!

            tvUsername = v.findViewById(R.id.tvUsername)
            tvWinrate = v.findViewById(R.id.tvWinrate)
            tvGamesPlayed = v.findViewById(R.id.tvGamesPlayed)
            pbWinrate = v.findViewById(R.id.pbWinrate)
        }
    }

    inner class MatchViewHolder internal constructor(v: View) : RecyclerView.ViewHolder(v) {

        // TODO Replace this area with match class as a custom view object
        private val tvWinLost: TextView
        private val tvGameName: TextView
        private val tvRoleName: TextView
        private val tvTeamName: TextView
        private val tvPlayerAmount: TextView
        private val imageView: ImageView


        init {
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener { v1 -> Log.d(TAG, "Element $adapterPosition clicked.") }

            tvWinLost = v.findViewById(R.id.matchWinLost)
            tvGameName = v.findViewById(R.id.matchGameName)
            tvRoleName = v.findViewById(R.id.matchRoleName)
            tvTeamName = v.findViewById(R.id.matchTeamName)
            tvPlayerAmount = v.findViewById(R.id.matchPlayerAmount)
            imageView = v.findViewById(R.id.matchGameImage)
        }
    }


    // Creates new view, does not assign data
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v: View

        if (viewType == PROFILE_HEADER_VIEW) {
            v = LayoutInflater.from(viewGroup.context).inflate(R.layout.element_profile_header, viewGroup, false)
            return HeaderViewHolder(v)
        }
        v = LayoutInflater.from(viewGroup.context).inflate(R.layout.element_match, viewGroup, false)
        return MatchViewHolder(v)
    }

    // Replace the contents of a view (invoked by the layout manager)
    // Method that assigns data to the view
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // TODO: Replace with model integration when match is implemented

        try {
            if (holder is MatchViewHolder) {
                val currentMatchPlayer = matches!![position]
                        .getMatchPlayerByUser(BoardbookSingleton.getInstance().authHandler.loggedInUser)

                holder.tvWinLost.text = if (currentMatchPlayer.win) "Winner" else "Looser"
                holder.tvGameName.text = matches!![position].game!!.name
                holder.tvPlayerAmount.text = matches!![position].matchPlayers.size.toString() + " Players"

                if (currentMatchPlayer.team!!.name != null) {
                    holder.tvTeamName.text = "In " + currentMatchPlayer.team!!
                    if (currentMatchPlayer.role!!.name != null)
                        holder.tvRoleName.text = "(" + currentMatchPlayer.role + ")"
                } else if (currentMatchPlayer.team!!.name == null && currentMatchPlayer.role!!.name != null) {
                    holder.tvTeamName.text = "as " + currentMatchPlayer.role!!.name!!
                    holder.tvRoleName.text = ""
                }



                holder.itemView.setOnClickListener { view ->
                    val intent = Intent(context, MatchDetailActivity::class.java)
                    intent.putExtra("Match", matches!![position].id)
                    context!!.startActivity(intent)
                }

                //vh.imageView.setImageURL();
            } else if (holder is HeaderViewHolder) {
                holder.tvUsername.text = user!!.name
                val stats = statisticsUtil.getWinrateFromMatches(matches!!, user)
                val percent = (100 * stats).toInt()
                holder.tvGamesPlayed.text = Integer.toString(user.matches.size)
                holder.tvWinrate.text = "$percent%"
                holder.pbWinrate.progress = percent
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun getItemCount(): Int {
        if (matches == null) {
            return 0
        }

        return if (user == null) matches!!.size else matches!!.size + 1

    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0 && user != null) {
            PROFILE_HEADER_VIEW
        } else super.getItemViewType(position)
    }

    companion object {

        private val TAG = "MatchfeedAdapter"
        private val PROFILE_HEADER_VIEW = 1
    }
}
