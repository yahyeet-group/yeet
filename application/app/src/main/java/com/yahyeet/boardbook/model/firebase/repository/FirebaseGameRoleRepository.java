package com.yahyeet.boardbook.model.firebase.repository;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yahyeet.boardbook.model.entity.GameRole;
import com.yahyeet.boardbook.model.repository.IGameRoleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class FirebaseGameRoleRepository extends AbstractFirebaseRepository<GameRole> implements IGameRoleRepository {
	public FirebaseGameRoleRepository(FirebaseFirestore firestore) {
		super(firestore);
	}

	@Override
	public FirebaseEntity<GameRole> fromModelEntityToFirebaseEntity(GameRole entity) {
		return FirebaseGameRole.fromModelType(entity);
	}

	@Override
	public FirebaseEntity<GameRole> fromDocumentToFirebaseEntity(DocumentSnapshot document) {
		return FirebaseGameRole.fromDocument(document);
	}

	@Override
	public String getCollectionName() {
		return "game_roles";
	}

	@Override
	public CompletableFuture<List<GameRole>> findRolesByTeamId(String id) {
		return CompletableFuture.completedFuture(new ArrayList<>());
	}
}
