package com.yahyeet.boardbook.model.firebase.repository

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.yahyeet.boardbook.model.entity.MatchPlayer
import com.yahyeet.boardbook.model.repository.IMatchPlayerRepository
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionException
import java.util.stream.Collectors

class FirebaseMatchPlayerRepository(firestore: FirebaseFirestore) : AbstractFirebaseRepository<MatchPlayer>(firestore), IMatchPlayerRepository {

    override val collectionName: String
        get() = "match_players"

    override fun fromModelEntityToFirebaseEntity(entity: MatchPlayer): AbstractFirebaseEntity<MatchPlayer> {
        return FirebaseMatchPlayer.fromModelType(entity)
    }

    override fun fromDocumentToFirebaseEntity(document: DocumentSnapshot): AbstractFirebaseEntity<MatchPlayer> {
        return FirebaseMatchPlayer.fromDocument(document)
    }

    override fun findMatchPlayersByMatchId(id: String): CompletableFuture<List<MatchPlayer>> {
        return CompletableFuture.supplyAsync<List<AbstractFirebaseEntity<MatchPlayer>>> {
            val task = firestore
                    .collection(collectionName)
                    .whereEqualTo("matchId", id)
                    .get()

            try {
                val querySnapshot = Tasks.await(task)

                return@CompletableFuture.supplyAsync querySnapshot
                        .getDocuments()
                        .stream()
                        .map(Function<DocumentSnapshot, AbstractFirebaseEntity<MatchPlayer>> { this.fromDocumentToFirebaseEntity(it) })
                        .collect(Collectors.toList<AbstractFirebaseEntity<MatchPlayer>>())
            } catch (e: Exception) {
                throw CompletionException(e)
            }
        }.thenApply { firebaseMatchPlayers ->
            firebaseMatchPlayers.forEach { firebaseMatchPlayer -> cache[firebaseMatchPlayer.id!!] = firebaseMatchPlayer }

            firebaseMatchPlayers
                    .stream()
                    .map<MatchPlayer>(Function<AbstractFirebaseEntity<MatchPlayer>, MatchPlayer> { it.toModelType() })
                    .collect<List<MatchPlayer>, Any>(Collectors.toList())
        }
    }

    override fun findMatchPlayersByUserId(id: String): CompletableFuture<List<MatchPlayer>> {
        return CompletableFuture.supplyAsync<List<AbstractFirebaseEntity<MatchPlayer>>> {
            val task = firestore
                    .collection(collectionName)
                    .whereEqualTo("playerId", id)
                    .get()

            try {
                val querySnapshot = Tasks.await(task)

                return@CompletableFuture.supplyAsync querySnapshot
                        .getDocuments()
                        .stream()
                        .map(Function<DocumentSnapshot, AbstractFirebaseEntity<MatchPlayer>> { this.fromDocumentToFirebaseEntity(it) })
                        .collect(Collectors.toList<AbstractFirebaseEntity<MatchPlayer>>())
            } catch (e: Exception) {
                throw CompletionException(e)
            }
        }.thenApply { firebaseMatchPlayers ->
            firebaseMatchPlayers.forEach { firebaseMatchPlayer -> cache[firebaseMatchPlayer.id!!] = firebaseMatchPlayer }

            firebaseMatchPlayers
                    .stream()
                    .map<MatchPlayer>(Function<AbstractFirebaseEntity<MatchPlayer>, MatchPlayer> { it.toModelType() })
                    .collect<List<MatchPlayer>, Any>(Collectors.toList())
        }
    }
}
