package com.yahyeet.boardbook.model.firebase.repository;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.yahyeet.boardbook.model.entity.GameTeam;
import com.yahyeet.boardbook.model.repository.IGameTeamRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;

/**
 * Firebase Firestore implementation of a game team repository
 */
public class FirebaseGameTeamRepository extends AbstractFirebaseRepository<GameTeam> implements IGameTeamRepository {
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

			return firebaseGameTeams
				.stream()
				.map(AbstractFirebaseEntity::toModelType)
				.collect(Collectors.toList());
		});
	}
}
