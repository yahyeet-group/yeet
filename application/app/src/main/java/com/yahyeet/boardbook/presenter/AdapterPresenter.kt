package com.yahyeet.boardbook.presenter

import android.os.Handler
import android.os.Looper

import com.yahyeet.boardbook.activity.IFutureInteractable
import com.yahyeet.boardbook.model.entity.AbstractEntity
import com.yahyeet.boardbook.model.handler.EntityHandler

import java.util.ArrayList

abstract class AdapterPresenter<E : AbstractEntity, H : EntityHandler<E>>(protected val fragment: IFutureInteractable) {

    protected var adapter: SearchAdapter<E>? = null
    private val database: MutableList<E>

    private val uiHandler = android.os.Handler(Looper.getMainLooper())

    init {
        database = ArrayList()
    }

    protected fun fillDatabase(handler: H) {
        fragment.disableViewInteraction()
        handler.all().thenAccept { initiatedGames ->
            if (initiatedGames != null) {
                database.addAll(initiatedGames)
            }

            uiHandler.post {
                fragment.enableViewInteraction()
                adapter!!.notifyDataSetChanged()
            }
        }.exceptionally { e ->
            uiHandler.post {
                fragment.displayLoadingFailed()
                fragment.enableViewInteraction()
            }
            null
        }
    }

    protected fun fillAndModifyDatabase(handler: H) {
        fragment.disableViewInteraction()
        handler.all().thenAccept { initiatedGames ->
            if (initiatedGames != null) {
                database.addAll(initiatedGames)
                modifyDatabase(database)

            }

            uiHandler.post {
                fragment.enableViewInteraction()
                adapter!!.notifyDataSetChanged()
            }
        }.exceptionally { e ->
            uiHandler.post {
                fragment.displayLoadingFailed()
                fragment.enableViewInteraction()
            }
            null
        }
    }

    protected fun updateAdapter() {
        adapter!!.notifyDataSetChanged()
    }

    protected fun getDatabase(): List<E> {
        return database
    }

    protected fun setDatabase(newDatbase: List<E>) {
        database.clear()
        database.addAll(newDatbase)
    }

    // To be overridden	, but not forced so not abstract
    protected open fun modifyDatabase(database: List<E>) {}
    // Called in fillAndModifyDatabase, override if database should not be all entities of type T
}
