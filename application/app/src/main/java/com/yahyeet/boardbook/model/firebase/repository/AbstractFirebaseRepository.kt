package com.yahyeet.boardbook.model.firebase.repository

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.yahyeet.boardbook.model.entity.AbstractEntity
import com.yahyeet.boardbook.model.repository.IRepository
import com.yahyeet.boardbook.model.repository.IRepositoryListener

import java.util.ArrayList
import java.util.HashMap
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionException
import java.util.concurrent.ExecutionException
import java.util.stream.Collectors

abstract class AbstractFirebaseRepository<TModel : AbstractEntity>(protected val firestore: FirebaseFirestore) : IRepository<TModel> {
    var collectionNamePrefix = ""

    private val cache = HashMap()
    private val listeners = ArrayList<IRepositoryListener<TModel>>()

    private val fullCollectionName: String
        get() = collectionNamePrefix + "_" + collectionName

    abstract val collectionName: String

    abstract fun fromModelEntityToFirebaseEntity(entity: TModel): AbstractFirebaseEntity<TModel>

    abstract fun fromDocumentToFirebaseEntity(document: DocumentSnapshot): AbstractFirebaseEntity<TModel>

    override fun save(entity: TModel): CompletableFuture<TModel> {
        if (entity.id ==

                null) {
            val futureFirebaseEntity = createFirebaseEntity(fromModelEntityToFirebaseEntity(entity))

            val futureOnSave = futureFirebaseEntity
                    .thenCompose { firebaseEntity -> afterSave(entity, firebaseEntity) }

            return futureFirebaseEntity
                    .thenCombine(futureOnSave) { firebaseEntity, nothing -> firebaseEntity }
                    .thenApply(Function<AbstractFirebaseEntity<TModel>, TModel> { it.toModelType() })
        } else {
            return findFirebaseEntityById(entity.id).thenCompose { firebaseEntity ->
                val futureFirebaseEntity = updateFirebaseEntity(fromModelEntityToFirebaseEntity(entity))

                val futureOnSave = futureFirebaseEntity
                        .thenCompose { fbEntity -> afterSave(entity, fbEntity) }

                futureFirebaseEntity
                        .thenCombine(futureOnSave) { fbEntity, nothing -> fbEntity }
                        .thenApply<TModel>(Function<AbstractFirebaseEntity<TModel>, TModel> { it.toModelType() })
            }.exceptionally { e ->
                try {
                    val futureFirebaseEntity = createFirebaseEntityWithId(fromModelEntityToFirebaseEntity(entity))

                    val futureOnSave = futureFirebaseEntity
                            .thenCompose { fbEntity -> afterSave(entity, fbEntity) }

                    return@findFirebaseEntityById entity.id.thenCompose(firebaseEntity -> {
                        CompletableFuture<AbstractFirebaseEntity<TModel>> futureFirebaseEntity =
                        updateFirebaseEntity(fromModelEntityToFirebaseEntity(entity));

                        CompletableFuture<Void> futureOnSave = futureFirebaseEntity
                                .thenCompose(fbEntity -> afterSave(entity, fbEntity));

                        return futureFirebaseEntity
                                .thenCombine(futureOnSave, fbEntity, nothing) -> fbEntity))
                        .thenApply(AbstractFirebaseEntity::toModelType);
                    }
                    ).exceptionally futureFirebaseEntity
                    .thenCombine<Void, AbstractFirebaseEntity<TModel>>(futureOnSave) { fbEntity, nothing -> fbEntity }
                            .thenApply(Function<AbstractFirebaseEntity<TModel>, TModel> { it.toModelType() })
                            .get()
                } catch (error: ExecutionException) {
                    throw CompletionException(error)
                } catch (error: InterruptedException) {
                    throw CompletionException(error)
                }
            }
        }
    }

    override fun find(id: String): CompletableFuture<TModel> {
        return findFirebaseEntityById(id).thenApply(Function<AbstractFirebaseEntity<TModel>, TModel> { it.toModelType() })
    }

    override fun delete(entity: TModel): CompletableFuture<Void> {
        return removeFirebaseEntityById(entity.id)
    }

    override fun all(): CompletableFuture<List<TModel>> {
        return findAllFirebaseEntities().thenApplyAsync { firebaseEntities ->
            firebaseEntities
                    .stream()
                    .map { firebaseEntity -> firebaseEntity.toModelType() }
                    .collect<List<TModel>, Any>(Collectors.toList())
        }
    }

    override fun addListener(listener: IRepositoryListener<TModel>) {

    }

    override fun removeListener(listener: IRepositoryListener<TModel>) {

    }

    open fun afterSave(entity: TModel, savedEntity: AbstractFirebaseEntity<TModel>): CompletableFuture<Void> {
        return CompletableFuture.completedFuture(null)
    }

    private fun createFirebaseEntity(firebaseEntity: AbstractFirebaseEntity<TModel>): CompletableFuture<AbstractFirebaseEntity<TModel>> {
        return CompletableFuture.supplyAsync<String> {
            val task = firestore.collection(fullCollectionName)
                    .add(firebaseEntity.toMap())

            try {
                val documentReference = Tasks.await(task)

                return@CompletableFuture.supplyAsync documentReference . getId ()
            } catch (e: Exception) {
                throw CompletionException(e)
            }
        }.thenCompose<AbstractFirebaseEntity<TModel>>(Function<String, CompletionStage<AbstractFirebaseEntity<TModel>>> { this.findFirebaseEntityById(it) }).thenApply { fbEntity ->
            cache.put(fbEntity.id!!, fbEntity)

            fbEntity
        }
    }

    private fun createFirebaseEntityWithId(firebaseEntity: AbstractFirebaseEntity<TModel>): CompletableFuture<AbstractFirebaseEntity<TModel>> {
        return CompletableFuture.supplyAsync<String> {
            val task = firestore.collection(fullCollectionName)
                    .document(firebaseEntity.id!!).set(firebaseEntity.toMap())

            try {
                Tasks.await(task)

                return@CompletableFuture.supplyAsync firebaseEntity . id
            } catch (e: Exception) {
                throw CompletionException(e)
            }
        }.thenCompose<AbstractFirebaseEntity<TModel>>(Function<String, CompletionStage<AbstractFirebaseEntity<TModel>>> { this.findFirebaseEntityById(it) }).thenApply { fbEntity ->
            cache.put(fbEntity.id!!, fbEntity)

            fbEntity
        }
    }

    private fun findFirebaseEntityById(id: String?): CompletableFuture<AbstractFirebaseEntity<TModel>> {
        val cachedEntity = cache.get(id)
        return if (cachedEntity != null) {
            CompletableFuture.completedFuture(cachedEntity)
        } else CompletableFuture.supplyAsync {
            val task = firestore.collection(fullCollectionName).document(id!!).get()

            try {
                val document = Tasks.await(task)

                if (document.exists()) {
                    return@CompletableFuture.supplyAsync fromDocumentToFirebaseEntity document
                }

                throw CompletionException(Exception("Entity not found"))
            } catch (e: Exception) {
                throw CompletionException(e)
            }
        }

    }

    private fun removeFirebaseEntityById(id: String?): CompletableFuture<Void> {
        return CompletableFuture.supplyAsync<Any> {
            val task = firestore.collection(fullCollectionName).document(id!!).delete()

            try {
                Tasks.await(task)

                return@CompletableFuture.supplyAsync null
            } catch (e: Exception) {
                throw CompletionException(e)
            }
        }.thenApply { nothing ->
            cache.remove(id)
            null
        }
    }

    private fun updateFirebaseEntity(firebaseEntity: AbstractFirebaseEntity<TModel>): CompletableFuture<AbstractFirebaseEntity<TModel>> {
        return CompletableFuture.supplyAsync<String> {
            val task = firestore.collection(fullCollectionName)
                    .document(firebaseEntity.id!!)
                    .update(firebaseEntity.toMap())

            try {
                Tasks.await(task)

                return@CompletableFuture.supplyAsync firebaseEntity . id
            } catch (e: Exception) {
                throw CompletionException(e)
            }
        }.thenApply { id ->
            cache.put(id, firebaseEntity)

            id
        }.thenCompose(Function<String, CompletionStage<AbstractFirebaseEntity<TModel>>> { this.findFirebaseEntityById(it) })
    }

    private fun findAllFirebaseEntities(): CompletableFuture<List<AbstractFirebaseEntity<TModel>>> {
        return CompletableFuture.supplyAsync {
            val task = firestore.collection(fullCollectionName).get()

            try {
                val querySnapshot = Tasks.await(task)

                return@CompletableFuture.supplyAsync querySnapshot
                        .getDocuments()
                        .stream()
                        .map(Function<DocumentSnapshot, AbstractFirebaseEntity<TModel>> { this.fromDocumentToFirebaseEntity(it) })
                        .collect(Collectors.toList<AbstractFirebaseEntity<TModel>>())
            } catch (e: Exception) {
                throw CompletionException(e)
            }
        }
    }

    protected fun getCache(): Map<String, AbstractFirebaseEntity<TModel>> {
        return cache
    }
}
