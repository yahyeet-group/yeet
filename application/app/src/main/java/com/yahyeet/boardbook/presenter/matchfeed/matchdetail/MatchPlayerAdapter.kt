package com.yahyeet.boardbook.presenter.matchfeed.matchdetail

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

import com.yahyeet.boardbook.R
import com.yahyeet.boardbook.model.entity.MatchPlayer

import java.util.ArrayList

class MatchPlayerAdapter(dataset: List<MatchPlayer>?, private val resources: Resources) : RecyclerView.Adapter<MatchPlayerAdapter.MatchPlayerViewHolder>() {

    private var matchPlayers: List<MatchPlayer>? = null

    internal class MatchPlayerViewHolder(v: View) : RecyclerView.ViewHolder(v) {


        // TODO Replace this area with match class as a custom view object
        private val tvPlayerName: TextView
        private val tvTeam: TextView
        private val constraintLayout: ConstraintLayout


        init {
            tvPlayerName = v.findViewById(R.id.tvMatchPlayerName)
            tvTeam = v.findViewById(R.id.tvMatchPlayerInfo)
            constraintLayout = v.findViewById(R.id.matchPlayerLayout)
        }
    }

    init {
        if (dataset != null)
            matchPlayers = dataset
        else {
            matchPlayers = ArrayList()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchPlayerAdapter.MatchPlayerViewHolder {
        val v = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.element_match_player, parent, false)

        return MatchPlayerViewHolder(v)
    }

    override fun onBindViewHolder(holder: MatchPlayerAdapter.MatchPlayerViewHolder, position: Int) {

        val current = matchPlayers!![position]

        holder.tvPlayerName.text = current.user!!.name

        if (current.role != null && current.role!!.team != null) {
            //TODO: Add in strings.xml
            holder.tvTeam.text = "Played in " + current.team!!.name + " (" + current.role!!.name + ")"
        } else if (current.team != null) {
            holder.tvTeam.text = "Played in " + current.team!!.name!!
        } else if (current.match!!.game!!.teams.size == 1) {
            holder.tvTeam.text = "Played as " + current.role!!.name!!
        } else {
            holder.tvTeam.text = ""
        }

        if (current.win) {
            holder
                    .constraintLayout
                    .setBackgroundColor(resources.getColor(R.color.colorMatchWon, null))
        } else {
            holder
                    .constraintLayout
                    .setBackgroundColor(resources.getColor(R.color.colorError, null))
        }


    }

    override fun getItemCount(): Int {
        return matchPlayers!!.size
    }
}
