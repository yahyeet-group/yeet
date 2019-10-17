package com.yahyeet.boardbook.model.firebase.repository;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.model.repository.IMatchRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class FirebaseMatchRepository extends AbstractFirebaseRepository<Match> implements IMatchRepository {
	public FirebaseMatchRepository(FirebaseFirestore firestore) {
		super(firestore);
	}

	@Override
	public FirebaseEntity<Match> fromModelEntityToFirebaseEntity(Match entity) {
		return null;
	}

	@Override
	public FirebaseEntity<Match> fromDocumentToFirebaseEntity(DocumentSnapshot document) {
		return null;
	}

	@Override
	public String getCollectionName() {
		return "matches";
	}

	@Override
	public CompletableFuture<List<Match>> findMatchesByGameId(String id) {
		return CompletableFuture.completedFuture(new ArrayList<>());
	}
}
