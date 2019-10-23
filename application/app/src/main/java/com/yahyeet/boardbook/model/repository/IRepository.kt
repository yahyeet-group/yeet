package com.yahyeet.boardbook.model.repository

import java.util.concurrent.CompletableFuture

interface IRepository<T> {
    fun find(id: String): CompletableFuture<T>

    fun save(entity: T): CompletableFuture<T>

    fun delete(entity: T): CompletableFuture<Void>

    fun all(): CompletableFuture<List<T>>

    fun addListener(listener: IRepositoryListener<T>)

    fun removeListener(listener: IRepositoryListener<T>)
}
