package com.yahyeet.boardbook.model.firebase.repository

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.yahyeet.boardbook.model.entity.Game
import com.yahyeet.boardbook.model.repository.IGameRepository

class FirebaseGameRepository(firestore: FirebaseFirestore) : AbstractFirebaseRepository<Game>(firestore), IGameRepository {

    override val collectionName: String
        get() = "games"

    override fun fromModelEntityToFirebaseEntity(entity: Game): AbstractFirebaseEntity<Game> {
        return FirebaseGame.fromModelType(entity)
    }

    override fun fromDocumentToFirebaseEntity(document: DocumentSnapshot): AbstractFirebaseEntity<Game> {
        return FirebaseGame.fromDocument(document)
    }
}
