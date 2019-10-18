package com.yahyeet.boardbook.model.firebase.repository;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.yahyeet.boardbook.model.entity.GameRole;
import com.yahyeet.boardbook.model.repository.IGameRoleRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;

public class FirebaseGameRoleRepository extends AbstractFirebaseRepository<GameRole> implements IGameRoleRepository {
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
		return CompletableFuture.supplyAsync(() -> {
			Task<QuerySnapshot> task =
				getFirestore()
					.collection(getCollectionName())
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

			return firebaseGameRoles
				.stream()
				.map(AbstractFirebaseEntity::toModelType)
				.collect(Collectors.toList());
		});
	}
}
