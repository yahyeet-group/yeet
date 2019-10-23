package com.yahyeet.boardbook.model.repository

interface IRepositoryListener<T> {
    fun onCreate(entity: T)

    fun onUpdate(entity: T)

    fun onDelete(entity: T)
}
