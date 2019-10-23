package com.yahyeet.boardbook.model.firebase.repository

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.yahyeet.boardbook.model.entity.Match
import com.yahyeet.boardbook.model.repository.IMatchRepository
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionException
import java.util.stream.Collectors

class FirebaseMatchRepository(firestore: FirebaseFirestore) : AbstractFirebaseRepository<Match>(firestore), IMatchRepository {

    override val collectionName: String
        get() = "matches"

    override fun fromModelEntityToFirebaseEntity(entity: Match): AbstractFirebaseEntity<Match> {
        return FirebaseMatch.fromModelType(entity)
    }

    override fun fromDocumentToFirebaseEntity(document: DocumentSnapshot): AbstractFirebaseEntity<Match> {
        return FirebaseMatch.fromDocument(document)
    }

    override fun findMatchesByGameId(id: String): CompletableFuture<List<Match>> {
        return CompletableFuture.supplyAsync<List<AbstractFirebaseEntity<Match>>> {
            val task = firestore
                    .collection(collectionName)
                    .whereEqualTo("gameId", id)
                    .get()

            try {
                val querySnapshot = Tasks.await(task)

                return@CompletableFuture.supplyAsync querySnapshot
                        .getDocuments()
                        .stream()
                        .map(Function<DocumentSnapshot, AbstractFirebaseEntity<Match>> { this.fromDocumentToFirebaseEntity(it) })
                        .collect(Collectors.toList<AbstractFirebaseEntity<Match>>())
            } catch (e: Exception) {
                throw CompletionException(e)
            }
        }.thenApply { firebaseMatches ->
            firebaseMatches.forEach { firebaseMatch -> cache[firebaseMatch.id!!] = firebaseMatch }

            firebaseMatches
                    .stream()
                    .map<Match>(Function<AbstractFirebaseEntity<Match>, Match> { it.toModelType() })
                    .collect<List<Match>, Any>(Collectors.toList())
        }
    }
}
