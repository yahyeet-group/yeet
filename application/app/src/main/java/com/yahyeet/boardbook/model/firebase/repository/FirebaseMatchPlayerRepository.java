package com.yahyeet.boardbook.model.firebase.repository;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.yahyeet.boardbook.model.entity.MatchPlayer;
import com.yahyeet.boardbook.model.repository.IMatchPlayerRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;

/**
 * Firebase Firestore implementation of a match player repository
 */
public class FirebaseMatchPlayerRepository extends AbstractFirebaseRepository<MatchPlayer> implements IMatchPlayerRepository {
	public FirebaseMatchPlayerRepository(FirebaseFirestore firestore) {
		super(firestore);
	}

	@Override
	public AbstractFirebaseEntity<MatchPlayer> fromModelEntityToFirebaseEntity(MatchPlayer entity) {
		return FirebaseMatchPlayer.fromModelType(entity);
	}

	@Override
	public AbstractFirebaseEntity<MatchPlayer> fromDocumentToFirebaseEntity(DocumentSnapshot document) {
		return FirebaseMatchPlayer.fromDocument(document);
	}

	@Override
	public String getCollectionName() {
		return "match_players";
	}

	@Override
	public CompletableFuture<List<MatchPlayer>> findMatchPlayersByMatchId(String id) {
		return CompletableFuture.supplyAsync(() -> {
			Task<QuerySnapshot> task =
				getFirestore()
					.collection(getFullCollectionName())
					.whereEqualTo("matchId", id)
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
		}).thenApply(firebaseMatchPlayers -> {
			firebaseMatchPlayers.forEach(firebaseMatchPlayer -> {
				getCache().put(firebaseMatchPlayer.getId(), firebaseMatchPlayer);
			});

			return firebaseMatchPlayers
				.stream()
				.map(AbstractFirebaseEntity::toModelType)
				.collect(Collectors.toList());
		});
	}

	@Override
	public CompletableFuture<List<MatchPlayer>> findMatchPlayersByUserId(String id) {
		return CompletableFuture.supplyAsync(() -> {
			Task<QuerySnapshot> task =
				getFirestore()
					.collection(getFullCollectionName())
					.whereEqualTo("playerId", id)
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
		}).thenApply(firebaseMatchPlayers -> {
			firebaseMatchPlayers.forEach(firebaseMatchPlayer -> {
				getCache().put(firebaseMatchPlayer.getId(), firebaseMatchPlayer);
			});

			return firebaseMatchPlayers
				.stream()
				.map(AbstractFirebaseEntity::toModelType)
				.collect(Collectors.toList());
		});
	}
}
