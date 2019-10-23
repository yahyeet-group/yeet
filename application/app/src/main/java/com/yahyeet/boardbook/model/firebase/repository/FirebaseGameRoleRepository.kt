package com.yahyeet.boardbook.model.firebase.repository

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.yahyeet.boardbook.model.entity.GameRole
import com.yahyeet.boardbook.model.repository.IGameRoleRepository
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionException
import java.util.stream.Collectors

class FirebaseGameRoleRepository(firestore: FirebaseFirestore) : AbstractFirebaseRepository<GameRole>(firestore), IGameRoleRepository {

    override val collectionName: String
        get() = "game_roles"

    override fun fromModelEntityToFirebaseEntity(entity: GameRole): AbstractFirebaseEntity<GameRole> {
        return FirebaseGameRole.fromModelType(entity)
    }

    override fun fromDocumentToFirebaseEntity(document: DocumentSnapshot): AbstractFirebaseEntity<GameRole> {
        return FirebaseGameRole.fromDocument(document)
    }

    override fun findRolesByTeamId(id: String): CompletableFuture<List<GameRole>> {
        return CompletableFuture.supplyAsync<List<AbstractFirebaseEntity<GameRole>>> {
            val task = firestore
                    .collection(collectionName)
                    .whereEqualTo("teamId", id)
                    .get()

            try {
                val querySnapshot = Tasks.await(task)

                return@CompletableFuture.supplyAsync querySnapshot
                        .getDocuments()
                        .stream()
                        .map(Function<DocumentSnapshot, AbstractFirebaseEntity<GameRole>> { this.fromDocumentToFirebaseEntity(it) })
                        .collect(Collectors.toList<AbstractFirebaseEntity<GameRole>>())
            } catch (e: Exception) {
                throw CompletionException(e)
            }
        }.thenApply { firebaseGameRoles ->
            firebaseGameRoles.forEach { firebaseGameRole -> cache[firebaseGameRole.id!!] = firebaseGameRole }

            firebaseGameRoles
                    .stream()
                    .map<GameRole>(Function<AbstractFirebaseEntity<GameRole>, GameRole> { it.toModelType() })
                    .collect<List<GameRole>, Any>(Collectors.toList())
        }
    }
}
