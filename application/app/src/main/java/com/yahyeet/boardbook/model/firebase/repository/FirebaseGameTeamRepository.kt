package com.yahyeet.boardbook.model.firebase.repository

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.yahyeet.boardbook.model.entity.GameTeam
import com.yahyeet.boardbook.model.repository.IGameTeamRepository
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionException
import java.util.stream.Collectors

class FirebaseGameTeamRepository(firestore: FirebaseFirestore) : AbstractFirebaseRepository<GameTeam>(firestore), IGameTeamRepository {

    override val collectionName: String
        get() = "game_teams"

    override fun fromModelEntityToFirebaseEntity(entity: GameTeam): AbstractFirebaseEntity<GameTeam> {
        return FirebaseGameTeam.fromModelType(entity)
    }

    override fun fromDocumentToFirebaseEntity(document: DocumentSnapshot): AbstractFirebaseEntity<GameTeam> {
        return FirebaseGameTeam.fromDocument(document)
    }

    override fun findTeamsByGameId(id: String): CompletableFuture<List<GameTeam>> {
        return CompletableFuture.supplyAsync<List<AbstractFirebaseEntity<GameTeam>>> {
            val task = firestore
                    .collection(collectionName)
                    .whereEqualTo("gameId", id)
                    .get()

            try {
                val querySnapshot = Tasks.await(task)

                return@CompletableFuture.supplyAsync querySnapshot
                        .getDocuments()
                        .stream()
                        .map(Function<DocumentSnapshot, AbstractFirebaseEntity<GameTeam>> { this.fromDocumentToFirebaseEntity(it) })
                        .collect(Collectors.toList<AbstractFirebaseEntity<GameTeam>>())
            } catch (e: Exception) {
                throw CompletionException(e)
            }
        }.thenApply { firebaseGameTeams ->
            firebaseGameTeams.forEach { firebaseGameTeam -> cache[firebaseGameTeam.id!!] = firebaseGameTeam }

            firebaseGameTeams
                    .stream()
                    .map<GameTeam>(Function<AbstractFirebaseEntity<GameTeam>, GameTeam> { it.toModelType() })
                    .collect<List<GameTeam>, Any>(Collectors.toList())
        }
    }
}
