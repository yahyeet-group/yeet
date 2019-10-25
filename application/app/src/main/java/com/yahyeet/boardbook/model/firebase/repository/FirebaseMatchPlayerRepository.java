package com.yahyeet.boardbook.model.firebase.repository;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.yahyeet.boardbook.model.entity.MatchPlayer;
import com.yahyeet.boardbook.model.repository.IMatchPlayerRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;

/**
 * Firebase Firestore implementation of a match player repository
 */
public class FirebaseMatchPlayerRepository extends AbstractFirebaseRepository<MatchPlayer> implements IMatchPlayerRepository {
	private Map<String, List<String>> matchMatchPlayerIdsCache = new HashMap<>();
	private Map<String, List<String>> userMatchPlayerIdsCache = new HashMap<>();

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
		List<String> cachedMatchPlayerIds = matchMatchPlayerIdsCache.get(id);
		if (cachedMatchPlayerIds != null) {
			List<CompletableFuture<MatchPlayer>> futureMatchPlayers =
				cachedMatchPlayerIds
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

			matchMatchPlayerIdsCache.put(id, firebaseMatchPlayers.stream().map(AbstractFirebaseEntity::getId).collect(Collectors.toList()));

			return firebaseMatchPlayers
				.stream()
				.map(AbstractFirebaseEntity::toModelType)
				.collect(Collectors.toList());
		});
	}

	@Override
	public CompletableFuture<List<MatchPlayer>> findMatchPlayersByUserId(String id) {
		List<String> cachedMatchPlayerIds = userMatchPlayerIdsCache.get(id);
		if (cachedMatchPlayerIds != null) {
			List<CompletableFuture<MatchPlayer>> futureMatchPlayers =
				cachedMatchPlayerIds
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

			userMatchPlayerIdsCache.put(id, firebaseMatchPlayers.stream().map(AbstractFirebaseEntity::getId).collect(Collectors.toList()));

			return firebaseMatchPlayers
				.stream()
				.map(AbstractFirebaseEntity::toModelType)
				.collect(Collectors.toList());
		});
	}

	@Override
	public CompletableFuture<Void> afterSave(MatchPlayer entity, AbstractFirebaseEntity<MatchPlayer> savedEntity) {
		return CompletableFuture.supplyAsync(() -> {
			List<String> cachedMatchMatchPlayerIds = matchMatchPlayerIdsCache.get(entity.getId());

			if (cachedMatchMatchPlayerIds != null && cachedMatchMatchPlayerIds.stream().noneMatch(matchPlayerId -> matchPlayerId.equals(entity.getId()))) {
				cachedMatchMatchPlayerIds.add(entity.getId());
			}

			List<String> cachedUserMatchPlayerIds = userMatchPlayerIdsCache.get(entity.getId());

			if (cachedUserMatchPlayerIds != null && cachedUserMatchPlayerIds.stream().noneMatch(matchPlayerId -> matchPlayerId.equals(entity.getId()))) {
				cachedUserMatchPlayerIds.add(entity.getId());
			}

			return null;
		});
	}

	@Override
	public CompletableFuture<Void> afterDelete(String matchPlayerId) {
		return CompletableFuture.supplyAsync(() -> {
			for (Map.Entry<String, List<String>> pair : matchMatchPlayerIdsCache.entrySet()) {
				if (pair.getValue().stream().anyMatch(cachedMatchPlayerId -> cachedMatchPlayerId.equals(matchPlayerId))) {
					matchMatchPlayerIdsCache.put(pair.getKey(), pair.getValue().stream().filter(cachedMatchPlayerId -> !cachedMatchPlayerId.equals(matchPlayerId)).collect(Collectors.toList()));
					break;
				}
			}

			for (Map.Entry<String, List<String>> pair : userMatchPlayerIdsCache.entrySet()) {
				if (pair.getValue().stream().anyMatch(cachedMatchPlayerId -> cachedMatchPlayerId.equals(matchPlayerId))) {
					userMatchPlayerIdsCache.put(pair.getKey(), pair.getValue().stream().filter(cachedMatchPlayerId -> !cachedMatchPlayerId.equals(matchPlayerId)).collect(Collectors.toList()));
					break;
				}
			}

			return null;
		});
	}
}
