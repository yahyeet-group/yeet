package com.yahyeet.boardbook.presenter

import android.widget.Filter

import androidx.recyclerview.widget.RecyclerView

import com.yahyeet.boardbook.model.entity.AbstractEntity

import java.util.ArrayList

abstract class SearchAdapter<T : AbstractEntity>(database: List<T>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var database: List<T>? = null
        private set
    private var allEntities: List<T>? = null
    val filter: Filter

    init {

        if (database != null)
            this.database = database
        else
            this.database = ArrayList()


        allEntities = ArrayList(this.database!!)
        filter = createNewFilter()

    }

    override fun getItemCount(): Int {
        return database!!.size
    }

    fun getAllEntities(): List<T> {
        if (database!!.size > 0 && allEntities!!.size == 0)
            allEntities = ArrayList(database!!)
        return allEntities
    }

    protected abstract fun createNewFilter(): Filter
}
