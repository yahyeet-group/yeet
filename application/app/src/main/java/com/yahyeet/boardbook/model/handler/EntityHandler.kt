package com.yahyeet.boardbook.model.handler

import com.yahyeet.boardbook.model.entity.AbstractEntity
import java.util.concurrent.CompletableFuture

interface EntityHandler<T : AbstractEntity> {

    fun find(id: String): CompletableFuture<T>

    fun save(entity: T): CompletableFuture<T>

    fun all(): CompletableFuture<List<T>>
}
