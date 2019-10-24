package com.yahyeet.boardbook.model.firebase.repository;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.model.repository.IGameRepository;

/**
 * Firebase Firestore implementation of a game repository
 */
public class FirebaseGameRepository extends AbstractFirebaseRepository<Game> implements IGameRepository {
	public FirebaseGameRepository(FirebaseFirestore firestore) {
		super(firestore);
	}

	@Override
	public AbstractFirebaseEntity<Game> fromModelEntityToFirebaseEntity(Game entity) {
		return FirebaseGame.fromModelType(entity);
	}

	@Override
	public AbstractFirebaseEntity<Game> fromDocumentToFirebaseEntity(DocumentSnapshot document) {
		return FirebaseGame.fromDocument(document);
	}

	@Override
	public String getCollectionName() {
		return "games";
	}
}
