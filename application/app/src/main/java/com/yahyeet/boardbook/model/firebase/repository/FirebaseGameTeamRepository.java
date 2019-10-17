package com.yahyeet.boardbook.model.firebase.repository;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yahyeet.boardbook.model.entity.GameTeam;
import com.yahyeet.boardbook.model.repository.IGameTeamRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class FirebaseGameTeamRepository extends AbstractFirebaseRepository<GameTeam> implements IGameTeamRepository {
	public FirebaseGameTeamRepository(FirebaseFirestore firestore) {
		super(firestore);
	}

	@Override
	public FirebaseEntity<GameTeam> fromModelEntityToFirebaseEntity(GameTeam entity) {
		return FirebaseGameTeam.fromModelType(entity);
	}

	@Override
	public FirebaseEntity<GameTeam> fromDocumentToFirebaseEntity(DocumentSnapshot document) {
		return FirebaseGameTeam.fromDocument(document);
	}

	@Override
	public String getCollectionName() {
		return "game_teams";
	}

	@Override
	public CompletableFuture<List<GameTeam>> findTeamsByGameId(String id) {
		return CompletableFuture.completedFuture(new ArrayList<>());
	}
}
