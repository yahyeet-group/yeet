package com.yahyeet.boardbook.presenter.matchcreation.selectplayers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

import com.yahyeet.boardbook.R
import com.yahyeet.boardbook.presenter.matchcreation.CMMasterPresenter
import com.yahyeet.boardbook.model.entity.User

import java.util.ArrayList

class PlayerAdapter(private val dataset: MutableList<User>, private val spp: SelectPlayersPresenter) : RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>(), Filterable {
    private val datasetFull: List<User>
    private val cmmp: CMMasterPresenter

    private val playerFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): Filter.FilterResults {
            val filteredList = ArrayList<User>()
            if (constraint == null || constraint.length == 0) {
                filteredList.addAll(datasetFull)
            } else {
                val filterPattern = constraint.toString().toLowerCase().trim({ it <= ' ' })

                for (user in datasetFull) {
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
            dataset.clear()
            dataset.addAll(results.values as List<*>)
            notifyDataSetChanged()
        }
    }

    internal inner class PlayerViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        var playerName: TextView
        var actionButton: Button

        init {
            playerName = v.findViewById(R.id.playerNameText)
            actionButton = v.findViewById(R.id.actionButton)

        }

    }


    init {
        this.datasetFull = ArrayList(dataset)
        cmmp = spp.masterPresenter

    }

    override fun onCreateViewHolder(vg: ViewGroup, viewType: Int): PlayerAdapter.PlayerViewHolder {

        val v = LayoutInflater.from(vg.context).inflate(R.layout.select_players_element, vg, false)


        return PlayerViewHolder(v)

    }

    override fun onBindViewHolder(holder: PlayerAdapter.PlayerViewHolder, position: Int) {

        holder.playerName.text = dataset[position].name
        holder.actionButton.setOnClickListener { event ->
            println(holder.actionButton.text.toString().toLowerCase())
            if (holder.actionButton.text.toString().toLowerCase() == "add") {
                holder.actionButton.text = "Remove"
                cmmp.cmdh.addSelectedPlayer(dataset[position])
            } else {
                holder.actionButton.text = "Add"
                cmmp.cmdh.removeSelectedPlayer(dataset[position])
            }
        }

    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun getFilter(): Filter {
        return playerFilter
    }
}
