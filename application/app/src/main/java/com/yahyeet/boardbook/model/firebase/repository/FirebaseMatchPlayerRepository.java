package com.yahyeet.boardbook.model.firebase.repository;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yahyeet.boardbook.model.entity.MatchPlayer;
import com.yahyeet.boardbook.model.repository.IMatchPlayerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class FirebaseMatchPlayerRepository extends AbstractFirebaseRepository<MatchPlayer> implements IMatchPlayerRepository {
	public FirebaseMatchPlayerRepository(FirebaseFirestore firestore) {
		super(firestore);
	}

	@Override
	public FirebaseEntity<MatchPlayer> fromModelEntityToFirebaseEntity(MatchPlayer entity) {
		return null;
	}

	@Override
	public FirebaseEntity<MatchPlayer> fromDocumentToFirebaseEntity(DocumentSnapshot document) {
		return null;
	}

	@Override
	public String getCollectionName() {
		return "match_players";
	}

	@Override
	public CompletableFuture<List<MatchPlayer>> findMatchPlayersByMatchId(String id) {
		return CompletableFuture.completedFuture(new ArrayList<>());
	}

	@Override
	public CompletableFuture<List<MatchPlayer>> findMatchPlayersByUserId(String id) {
		return CompletableFuture.completedFuture(new ArrayList<>());
	}
}
