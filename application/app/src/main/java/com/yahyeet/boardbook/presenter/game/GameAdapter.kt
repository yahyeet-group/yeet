package com.yahyeet.boardbook.presenter.game

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.yahyeet.boardbook.R
import com.yahyeet.boardbook.activity.home.game.gamedetail.GameDetailActivity
import com.yahyeet.boardbook.model.entity.Game
import com.yahyeet.boardbook.presenter.SearchAdapter

import java.util.ArrayList

class GameAdapter(database: List<Game>, private val context: Context, private var displayType: DisplayType?) : SearchAdapter<Game>(database) {

    internal class GameListViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        var textViewName: TextView
        var textViewDifficulty: TextView
        var textViewPlayers: TextView
        var textViewTeams: TextView


        init {
            // Define click listener for the ViewHolder's View.
            textViewName = v.findViewById(R.id.gameSeachName)
            textViewDifficulty = v.findViewById(R.id.gameDifficulty)
            textViewPlayers = v.findViewById(R.id.gameListMinMaxPlayers)
            textViewTeams = v.findViewById(R.id.gameListTeamAmount)

        }
    }

    internal class GameGridViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        // TODO Replace this area with match class as a custom view object
        var textViewName: TextView

        init {
            // Define click listener for the ViewHolder's View.
            textViewName = v.findViewById(R.id.gameGridName)

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v: View

        if (viewType == GAME_LIST) {
            v = LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.element_game_list, parent, false)
            return GameListViewHolder(v)
        }
        v = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.element_game_grid, parent, false)
        return GameGridViewHolder(v)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val database = database

        holder.itemView.setOnClickListener { view ->
            val intent = Intent(context, GameDetailActivity::class.java)
            intent.putExtra("Game", database!![position].id)
            context.startActivity(intent)
        }



        if (holder is GameListViewHolder) {

            holder.textViewName.text = database!![position].name
            holder.textViewDifficulty.text = getDifficulty(database[position].difficulty)
            holder.textViewPlayers.text = database[position].minPlayers.toString() + " - " + database[position].maxPlayers + " Players"
            holder.textViewTeams.text = "0 - " + database[position].teams.size + " Teams"

        } else if (holder is GameGridViewHolder) {

            holder.textViewName.text = database!![position].name
        }


    }


    override fun getItemViewType(position: Int): Int {
        return if (displayType == DisplayType.LIST)
            GAME_LIST
        else if (displayType == DisplayType.GRID)
            GAME_GRID
        else
            super.getItemViewType(position)
    }

    fun setDisplayType(displayType: DisplayType) {
        this.displayType = displayType
    }

    override fun createNewFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): Filter.FilterResults {
                val filteredList = ArrayList<Game>()
                if (constraint == null || constraint.length == 0) {
                    filteredList.addAll(allEntities)
                } else {
                    val filterPattern = constraint.toString().toLowerCase().trim({ it <= ' ' })

                    for (game in allEntities) {
                        if (game.name!!.toLowerCase().contains(filterPattern)) {
                            filteredList.add(game)
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


    private fun getDifficulty(i: Int): String {
        if (i == 1) {
            return "Easy"
        } else if (i == 2) {
            return "Medium"
        } else if (i == 3) {
            return "Hard"
        }
        return "Unknown Difficulty"
    }

    companion object {

        private val TAG = "GameAdapter"


        private val GAME_LIST = 0
        private val GAME_GRID = 1
    }
}
