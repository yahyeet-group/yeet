package com.yahyeet.boardbook.model.firebase.repository;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.yahyeet.boardbook.model.entity.GameTeam;
import com.yahyeet.boardbook.model.repository.IGameTeamRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;

/**
 * Firebase Firestore implementation of a game team repository
 */
public class FirebaseGameTeamRepository extends AbstractFirebaseRepository<GameTeam> implements IGameTeamRepository {
	private Map<String, List<String>> gameTeamsIdsCache = new HashMap<>();

	public FirebaseGameTeamRepository(FirebaseFirestore firestore) {
		super(firestore);
	}

	@Override
	public AbstractFirebaseEntity<GameTeam> fromModelEntityToFirebaseEntity(GameTeam entity) {
		return FirebaseGameTeam.fromModelType(entity);
	}

	@Override
	public AbstractFirebaseEntity<GameTeam> fromDocumentToFirebaseEntity(DocumentSnapshot document) {
		return FirebaseGameTeam.fromDocument(document);
	}

	@Override
	public String getCollectionName() {
		return "game_teams";
	}

	@Override
	public CompletableFuture<List<GameTeam>> findTeamsByGameId(String id) {
		List<String> cachedGameTeamsIds = gameTeamsIdsCache.get(id);
		if (cachedGameTeamsIds != null) {
			List<CompletableFuture<GameTeam>> futureGameTeams = cachedGameTeamsIds.stream().map(this::find).collect(Collectors.toList());

			return CompletableFuture
				.allOf(futureGameTeams.toArray(new CompletableFuture[0]))
				.thenApply(future -> futureGameTeams.stream().map(CompletableFuture::join).collect(Collectors.toList()));
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
		}).thenApply(firebaseGameTeams -> {
			firebaseGameTeams.forEach(firebaseGameTeam -> {
				getCache().put(firebaseGameTeam.getId(), firebaseGameTeam);
			});

			gameTeamsIdsCache.put(id, firebaseGameTeams.stream().map(AbstractFirebaseEntity::getId).collect(Collectors.toList()));

			return firebaseGameTeams
				.stream()
				.map(AbstractFirebaseEntity::toModelType)
				.collect(Collectors.toList());
		});
	}


	@Override
	public CompletableFuture<Void> afterSave(GameTeam entity, AbstractFirebaseEntity<GameTeam> savedEntity) {
		return CompletableFuture.supplyAsync(() -> {
			List<String> cachedTeamIds = gameTeamsIdsCache.get(entity.getGame().getId());

			if (cachedTeamIds != null && cachedTeamIds.stream().noneMatch(teamId -> teamId.equals(entity.getId()))) {
				cachedTeamIds.add(entity.getId());
			}

			return null;
		});
	}

	@Override
	public CompletableFuture<Void> afterDelete(String teamId) {
		return CompletableFuture.supplyAsync(() -> {
			for (Map.Entry<String, List<String>> pair : gameTeamsIdsCache.entrySet()) {
				if (pair.getValue().stream().anyMatch(cachedTeamId -> cachedTeamId.equals(teamId))) {
					gameTeamsIdsCache.put(pair.getKey(), pair.getValue().stream().filter(cachedTeamId -> !cachedTeamId.equals(teamId)).collect(Collectors.toList()));
					break;
				}
			}

			return null;
		});
	}
}
