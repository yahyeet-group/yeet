package com.yahyeet.boardbook.firebase.repository;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.yahyeet.boardbook.model.entity.GameRole;
import com.yahyeet.boardbook.model.repository.IGameRoleRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;

/**
 * Firebase Firestore implementation of a game role repository
 */
public class FirebaseGameRoleRepository extends AbstractFirebaseRepository<GameRole> implements IGameRoleRepository {
	private Map<String, List<String>> teamRoleIdsCache = new HashMap<>();

	public FirebaseGameRoleRepository(FirebaseFirestore firestore) {
		super(firestore);
	}

	@Override
	public AbstractFirebaseEntity<GameRole> fromModelEntityToFirebaseEntity(GameRole entity) {
		return FirebaseGameRole.fromModelType(entity);
	}

	@Override
	public AbstractFirebaseEntity<GameRole> fromDocumentToFirebaseEntity(DocumentSnapshot document) {
		return FirebaseGameRole.fromDocument(document);
	}

	@Override
	public String getCollectionName() {
		return "game_roles";
	}

	@Override
	public CompletableFuture<List<GameRole>> findRolesByTeamId(String id) {
		List<String> cachedGameRoleIds = teamRoleIdsCache.get(id);
		if (cachedGameRoleIds != null) {
			List<CompletableFuture<GameRole>> futureGameRoles = cachedGameRoleIds.stream().map(this::find).collect(Collectors.toList());

			return CompletableFuture
				.allOf(futureGameRoles.toArray(new CompletableFuture[0]))
				.thenApply(future -> futureGameRoles
					.stream()
					.map(CompletableFuture::join)
					.collect(Collectors.toList())
				);
		}

		return CompletableFuture.supplyAsync(() -> {
			Task<QuerySnapshot> task =
				getFirestore()
					.collection(getFullCollectionName())
					.whereEqualTo("teamId", id)
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
		}).thenApply(firebaseGameRoles -> {
			firebaseGameRoles.forEach(firebaseGameRole -> {
				getCache().put(firebaseGameRole.getId(), firebaseGameRole);
			});

			//teamRoleIdsCache.put(id, firebaseGameRoles.stream().map(AbstractFirebaseEntity::getId).collect(Collectors.toList()));

			return firebaseGameRoles
				.stream()
				.map(AbstractFirebaseEntity::toModelType)
				.collect(Collectors.toList());
		});
	}

	@Override
	public CompletableFuture<Void> afterSave(GameRole entity, AbstractFirebaseEntity<GameRole> savedEntity) {
		return CompletableFuture.supplyAsync(() -> {
			List<String> cachedRoleIds = teamRoleIdsCache.get(entity.getTeam().getId());

			if (cachedRoleIds != null && cachedRoleIds.stream().noneMatch(roleId -> roleId.equals(entity.getId()))) {
				cachedRoleIds.add(entity.getId());
			}

			return null;
		});
	}

	@Override
	public CompletableFuture<Void> afterDelete(String roleId) {
		return CompletableFuture.supplyAsync(() -> {
			for (Map.Entry<String, List<String>> pair : teamRoleIdsCache.entrySet()) {
				if (pair.getValue().stream().anyMatch(cachedRoleId -> cachedRoleId.equals(roleId))) {
					//teamRoleIdsCache.put(pair.getKey(), pair.getValue().stream().filter(cachedRoleId -> !cachedRoleId.equals(roleId)).collect(Collectors.toList()));
					break;
				}
			}

			return null;
		});
	}
}
