package com.yahyeet.boardbook.presenter.game.gamedetail


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

import com.yahyeet.boardbook.R
import com.yahyeet.boardbook.model.entity.GameRole
import com.yahyeet.boardbook.model.entity.GameTeam

import java.util.ArrayList

class GameDetailAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private var firstList: List<GameTeam>? = ArrayList()
    private var secondList: List<List<GameRole>>? = ArrayList()

    private val allNames = ArrayList<String>()

    constructor() {}

    constructor(firstList: List<GameTeam>, secondList: List<List<GameRole>>) {
        this.firstList = firstList
        this.secondList = secondList

        for (team in firstList) {
            allNames.add(team.name)
            for (role in team.roles)
                allNames.add(role.name)
        }

    }

    fun setFirstList(firstList: List<GameTeam>) {
        this.firstList = firstList
    }

    fun setSecondList(secondList: List<List<GameRole>>) {
        this.secondList = secondList
    }

    open inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // List items of first list
        private val tvTeamName: TextView

        // List items of second list
        private val tvRoleName: TextView


        init {

            // Get the view of the elements of first list
            tvTeamName = itemView.findViewById<View>(R.id.gameDetailTeamName) as TextView

            // Get the view of the elements of second list
            tvRoleName = itemView.findViewById<View>(R.id.gameDetailRoleName) as TextView
        }

        fun bindViewSecondList(pos: Int) {

            val description = allNames[pos]

            tvRoleName.text = description
        }

        fun bindViewFirstList(pos: Int) {

            val description = allNames[pos]

            tvTeamName.text = description
        }


    }

    private inner class FirstListItemViewHolder(itemView: View) : ViewHolder(itemView)


    private inner class SecondListItemViewHolder(itemView: View) : ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val v: View

        if (viewType == FIRST_LIST_ITEM_VIEW) {
            v = LayoutInflater.from(parent.context).inflate(R.layout.list_item_team, parent, false)
            return FirstListItemViewHolder(v)

        } else {
            // SECOND_LIST_ITEM_VIEW
            v = LayoutInflater.from(parent.context).inflate(R.layout.list_item_role, parent, false)
            return SecondListItemViewHolder(v)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        try {
            if (holder is SecondListItemViewHolder) {
                holder.bindViewSecondList(position)

            } else if (holder is FirstListItemViewHolder) {
                holder.bindViewFirstList(position)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun getItemCount(): Int {

        var firstListSize = 0
        var secondListSize = 0

        if (secondList == null && firstList == null) return 0


        if (firstList != null)
            firstListSize = firstList!!.size


        for (list in secondList!!) {
            for (role in list)
                secondListSize++
        }

        return firstListSize + secondListSize
    }

    override fun getItemViewType(position: Int): Int {


        if (secondList == null && firstList == null)
            return super.getItemViewType(position)

        if (secondList == null)
            return FIRST_LIST_ITEM_VIEW

        if (firstList == null)
            return SECOND_LIST_ITEM_VIEW


        val sizeList = ArrayList<Int>()

        for (list in secondList!!)
            sizeList.add(list.size)

        var desiredPosition = position

        var sumOfPreviousTeamSizes = 0
        for (i in sizeList.indices) {
            if (sumOfPreviousTeamSizes + sizeList[i] >= position) {
                desiredPosition = position - sumOfPreviousTeamSizes - i
                break
            } else {
                sumOfPreviousTeamSizes += sizeList[i]
            }
        }

        return if (desiredPosition == 0)
            FIRST_LIST_ITEM_VIEW
        else
            SECOND_LIST_ITEM_VIEW
    }

    companion object {

        private val FIRST_LIST_ITEM_VIEW = 1
        private val SECOND_LIST_ITEM_VIEW = 2
    }
}
