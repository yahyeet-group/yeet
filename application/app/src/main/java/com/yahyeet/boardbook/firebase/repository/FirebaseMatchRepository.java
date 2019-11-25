package com.yahyeet.boardbook.firebase.repository;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.model.repository.IMatchRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;

/**
 * Firebase Firestore implementation of a match repository
 */
public class FirebaseMatchRepository extends AbstractFirebaseRepository<Match> implements IMatchRepository {
	private Map<String, List<String>> gameMatchIdsCache = new HashMap<>();

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
		List<String> cachedMatchIds = gameMatchIdsCache.get(id);
		if (cachedMatchIds != null) {
			List<CompletableFuture<Match>> futureMatchPlayers =
				cachedMatchIds
					.stream()
					.map(this::find)
					.collect(Collectors.toList());

			return CompletableFuture.allOf(futureMatchPlayers.toArray(new CompletableFuture[0]))
				.thenApply(future -> futureMatchPlayers.stream().map(CompletableFuture::join).collect(Collectors.toList()));
		}

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

			//gameMatchIdsCache.put(id, firebaseMatches.stream().map(AbstractFirebaseEntity::getId).collect(Collectors.toList()));

			return firebaseMatches
				.stream()
				.map(AbstractFirebaseEntity::toModelType)
				.collect(Collectors.toList());
		});
	}

	@Override
	public CompletableFuture<Void> afterSave(Match entity, AbstractFirebaseEntity<Match> savedEntity) {
		return CompletableFuture.supplyAsync(() -> {
			List<String> cachedMatchIds = gameMatchIdsCache.get(entity.getGame().getId());

			if (cachedMatchIds != null && cachedMatchIds.stream().noneMatch(matchId -> matchId.equals(entity.getId()))) {
				cachedMatchIds.add(entity.getId());
			}

			return null;
		});
	}

	@Override
	public CompletableFuture<Void> afterDelete(String matchId) {
		return CompletableFuture.supplyAsync(() -> {
			for (Map.Entry<String, List<String>> pair : gameMatchIdsCache.entrySet()) {
				if (pair.getValue().stream().anyMatch(cachedMatchId -> cachedMatchId.equals(matchId))) {
					//gameMatchIdsCache.put(pair.getKey(), pair.getValue().stream().filter(cachedMatchId -> !cachedMatchId.equals(matchId)).collect(Collectors.toList()));
					break;
				}
			}

			return null;
		});
	}
}
