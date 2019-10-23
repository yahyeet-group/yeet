package com.yahyeet.boardbook.presenter.matchcreation.selectgame

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

import com.yahyeet.boardbook.R
import com.yahyeet.boardbook.presenter.matchcreation.CMMasterPresenter
import com.yahyeet.boardbook.model.entity.Game


class GamesAdapter(private val dataset: List<Game>, private val sgp: SelectGamePresenter) : RecyclerView.Adapter<GamesAdapter.GamesViewHolder>() {
    private val cmmp: CMMasterPresenter


    internal inner class GamesViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        private val gameTitle: TextView
        private val gameImage: ImageView
        private val selectGameButton: Button


        init {

            gameTitle = v.findViewById(R.id.gamesTitle)
            gameImage = v.findViewById(R.id.selectGameImage)
            selectGameButton = v.findViewById(R.id.selectGameButton)

        }
    }

    init {
        cmmp = sgp.masterPresenter

    }


    override fun onCreateViewHolder(vg: ViewGroup, viewType: Int): GamesViewHolder {

        val v = LayoutInflater.from(vg.context).inflate(R.layout.element_select_game, vg, false)

        return GamesViewHolder(v)

    }

    override fun onBindViewHolder(holder: GamesViewHolder, position: Int) {

        holder.gameTitle.text = dataset[position].name
        holder.itemView.findViewById<View>(R.id.selectGameButton).setOnClickListener { event ->
            cmmp.goToSelectPlayers()
            cmmp.cmdh.game = dataset[position]
            // Not working if actually implemented
            //System.out.println(dataset.get(position).getTeams().get(0).getRoles().get(0).getName());
        }

    }

    override fun getItemCount(): Int {
        return dataset.size
    }

}
