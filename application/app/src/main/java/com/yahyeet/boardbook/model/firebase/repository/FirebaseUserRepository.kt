package com.yahyeet.boardbook.model.firebase.repository

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.yahyeet.boardbook.model.entity.User
import com.yahyeet.boardbook.model.repository.IUserRepository

import java.util.HashMap
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionException
import java.util.stream.Collectors

class FirebaseUserRepository(firestore: FirebaseFirestore) : AbstractFirebaseRepository<User>(firestore), IUserRepository {

    override val collectionName: String
        get() = "users"

    private val friendsCollectionName: String
        get() = collectionNamePrefix + "_friends_user_to_user"

    override fun fromModelEntityToFirebaseEntity(entity: User): AbstractFirebaseEntity<User> {
        return FirebaseUser.fromModelType(entity)
    }

    override fun fromDocumentToFirebaseEntity(document: DocumentSnapshot): AbstractFirebaseEntity<User> {
        return FirebaseUser.fromDocument(document)
    }

    override fun findFriendsByUserId(id: String?): CompletableFuture<List<User>> {
        val friendsPartOne = CompletableFuture.supplyAsync<List<String>> {
            val task = firestore
                    .collection(friendsCollectionName)
                    .whereEqualTo("a", id)
                    .get()

            try {
                val querySnapshot = Tasks.await(task)

                return@CompletableFuture.supplyAsync querySnapshot
                        .getDocuments()
                        .stream()
                        .map({ document -> document.getString("b") })
                        .collect(Collectors.toList<String>())
            } catch (e: Exception) {
                throw CompletionException(e)
            }
        }.thenCompose { friendIds ->
            val friendsFuture = friendIds
                    .stream()
                    .map<CompletableFuture<User>>(Function<String, CompletableFuture<User>> { this.find(it) })
                    .collect<List<CompletableFuture<User>>, Any>(Collectors.toList())

            val allOfFriendsFuture = CompletableFuture.allOf(*friendsFuture.toTypedArray<CompletableFuture>())

            allOfFriendsFuture.thenApply { future -> friendsFuture.stream().map<User>(Function<CompletableFuture<User>, User> { it.join() }).collect<List<User>, Any>(Collectors.toList()) }
        }

        val friendsPartTwo = CompletableFuture.supplyAsync<List<String>> {
            val task = firestore
                    .collection(friendsCollectionName)
                    .whereEqualTo("b", id)
                    .get()

            try {
                val querySnapshot = Tasks.await(task)

                return@CompletableFuture.supplyAsync querySnapshot
                        .getDocuments()
                        .stream()
                        .map({ document -> document.getString("a") })
                        .collect(Collectors.toList<String>())
            } catch (e: Exception) {
                throw CompletionException(e)
            }
        }.thenCompose { friendIds ->
            val friendsFuture = friendIds
                    .stream()
                    .map { friendId -> find(friendId) }
                    .collect<List<CompletableFuture<User>>, Any>(Collectors.toList())

            val allOfFriendsFuture = CompletableFuture.allOf(*friendsFuture.toTypedArray<CompletableFuture>())

            allOfFriendsFuture.thenApply { future -> friendsFuture.stream().map<User>(Function<CompletableFuture<User>, User> { it.join() }).collect<List<User>, Any>(Collectors.toList()) }
        }

        return friendsPartOne
                .thenCombine(friendsPartTwo) { partOne, partTwo ->
                    partOne.addAll(partTwo)
                    partOne
                }
    }

    override fun afterSave(entity: User, savedEntity: AbstractFirebaseEntity<User>): CompletableFuture<Void> {
        return findFriendsByUserId(savedEntity.id).thenApply { remoteFriends ->
            entity
                    .friends
                    .stream()
                    .filter { localFriend ->
                        remoteFriends
                                .stream()
                                .noneMatch { remoteFriend -> remoteFriend == localFriend }
                    }
                    .collect<List<User>, Any>(Collectors.toList()
                    )
        }
                .thenCompose { addedFriends ->
                    val addedFriendsFuture = addedFriends
                            .stream()
                            .map { addedFriend -> addFriend(entity.id, addedFriend.id) }
                            .collect<List<CompletableFuture<Void>>, Any>(Collectors.toList())

                    CompletableFuture.allOf(*addedFriendsFuture.toTypedArray<CompletableFuture>())
                }
    }

    private fun addFriend(leftId: String?, rightId: String?): CompletableFuture<Void> {
        return CompletableFuture.supplyAsync {
            val data = HashMap<String, Any>()
            data["a"] = leftId!!
            data["b"] = rightId!!
            val task = firestore.collection(friendsCollectionName).add(data)

            try {
                Tasks.await(task)

                return@CompletableFuture.supplyAsync null
            } catch (e: Exception) {
                throw CompletionException(e)
            }
        }
    }
}
