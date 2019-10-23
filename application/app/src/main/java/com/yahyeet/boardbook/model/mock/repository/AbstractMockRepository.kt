package com.yahyeet.boardbook.model.mock.repository

import com.yahyeet.boardbook.model.entity.AbstractEntity
import com.yahyeet.boardbook.model.repository.IRepository
import com.yahyeet.boardbook.model.repository.IRepositoryListener

import java.lang.reflect.Array
import java.util.ArrayList
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionException
import java.util.concurrent.atomic.AtomicBoolean
import java.util.function.Function

open class AbstractMockRepository<T : AbstractEntity> : IRepository<T> {

    protected var mockDatabase: MutableList<T> = ArrayList()
    protected var idCount = 0

    override fun find(id: String): CompletableFuture<T> {
        return CompletableFuture.supplyAsync {

            for (t in mockDatabase) {
                if (t.id == id)
                    return@CompletableFuture.supplyAsync t
            }

            throw CompletionException(Exception("No user found"))
        }
    }

    override fun save(entity: T): CompletableFuture<T> {
        val create = { T ->
            entity.id = Integer.toString(idCount)
            idCount++
            mockDatabase.add(entity)
            entity
        }

        return CompletableFuture.supplyAsync {
            if (entity.id == null) {
                return@CompletableFuture.supplyAsync create . apply entity
            } else {
                val found = AtomicBoolean(false)
                mockDatabase.forEach { m ->
                    if (m == entity) {
                        val index = mockDatabase.indexOf(m)
                        mockDatabase[index] = entity
                        found.set(true)
                    }
                }

                if (!found.get()) {
                    return@CompletableFuture.supplyAsync create . apply entity
                }

                return@CompletableFuture.supplyAsync entity
            }
        }
    }

    override fun delete(entity: T): CompletableFuture<Void> {
        return CompletableFuture.supplyAsync {
            for (i in mockDatabase.indices) {
                if (entity.id == mockDatabase[i].id) {
                    mockDatabase.removeAt(i)
                    return@CompletableFuture.supplyAsync null
                }
            }

            throw CompletionException(Exception("Not a user in database"))
        }
    }

    override fun all(): CompletableFuture<List<T>> {
        return CompletableFuture.supplyAsync { mockDatabase }
    }

    override fun addListener(listener: IRepositoryListener<*>) {

    }

    override fun removeListener(listener: IRepositoryListener<*>) {

    }
}
