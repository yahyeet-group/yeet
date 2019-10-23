package com.yahyeet.boardbook.model.firebase.repository;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.model.repository.IMatchRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;

public class FirebaseMatchRepository extends AbstractFirebaseRepository<Match> implements IMatchRepository {
	public FirebaseMatchRepository(FirebaseFirestore firestore) {
		super(firestore);
	}

	@Override
	public AbstractFirebaseEntity<Match> fromModelEntityToFirebaseEntity(Match entity) {
		return FirebaseMatch.fromModelType(entity);
	}

	@Override
	public AbstractFirebaseEntity<Match> fromDocumentToFirebaseEntity(DocumentSnapshot document) {
		return FirebaseMatch.fromDocument(document);
	}

	@Override
	public String getCollectionName() {
		return "matches";
	}

	@Override
	public CompletableFuture<List<Match>> findMatchesByGameId(String id) {
		return CompletableFuture.supplyAsync(() -> {
			Task<QuerySnapshot> task =
				getFirestore()
					.collection(getFullCollectionName())
					.whereEqualTo("gameId", id)
					.get();

			try {
				QuerySnapshot querySnapshot = Tasks.await(task);

				return querySnapshot
					.getDocuments()
					.stream()
					.map(this::fromDocumentToFirebaseEntity)
					.collect(Collectors.toList());
			} catch (Exception e) {
				throw new CompletionException(e);
			}
		}).thenApply(firebaseMatches -> {
			firebaseMatches.forEach(firebaseMatch -> {
				getCache().put(firebaseMatch.getId(), firebaseMatch);
			});

			return firebaseMatches
				.stream()
				.map(AbstractFirebaseEntity::toModelType)
				.collect(Collectors.toList());
		});
	}
}
