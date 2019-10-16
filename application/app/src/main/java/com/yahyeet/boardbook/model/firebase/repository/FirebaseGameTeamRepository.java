package com.yahyeet.boardbook.model.firebase.repository;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.yahyeet.boardbook.model.entity.GameTeam;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;

public class FirebaseGameTeamRepository {
	private FirebaseFirestore firestore;

	public static final String COLLECTION_NAME = "game_teams";

	public FirebaseGameTeamRepository(FirebaseFirestore firestore) {
		this.firestore = firestore;
	}

	public CompletableFuture<GameTeam> create(GameTeam gameTeamRole) {
		return createFirebaseGameTeam(FirebaseGameTeam.fromGameTeam(gameTeamRole)).thenApplyAsync(FirebaseGameTeam::toGameTeam);

	}

	public CompletableFuture<GameTeam> find(String id) {
		return findFirebaseGameTeamById(id).thenApplyAsync(FirebaseGameTeam::toGameTeam);
	}


	public CompletableFuture<GameTeam> update(GameTeam gameTeamRole) {
		return updateFirebaseGameTeam(FirebaseGameTeam.fromGameTeam(gameTeamRole)).thenApplyAsync(FirebaseGameTeam::toGameTeam);
	}


	public CompletableFuture<Void> remove(GameTeam gameTeamRole) {
		return removeFirebaseGameTeamById(gameTeamRole.getId());
	}

	public CompletableFuture<List<GameTeam>> all() {
		return findAllFirebaseGameTeams().thenApplyAsync(firebaseUsers ->
			firebaseUsers
				.stream()
				.map(FirebaseGameTeam::toGameTeam)
				.collect(Collectors.toList())
		);
	}


	private CompletableFuture<FirebaseGameTeam> createFirebaseGameTeam(FirebaseGameTeam firebaseGameTeam) {
		return CompletableFuture.supplyAsync(() -> {
			Task<DocumentReference> task = firestore.collection(COLLECTION_NAME)
				.add(firebaseGameTeam.toMap());

			try {
				DocumentReference documentReference = Tasks.await(task);

				return documentReference.getId();
			} catch (Exception e) {
				throw new CompletionException(e);
			}
		}).thenCompose(this::findFirebaseGameTeamById);
	}

	private CompletableFuture<FirebaseGameTeam> findFirebaseGameTeamById(String id) {
		return CompletableFuture.supplyAsync(() -> {
			Task<DocumentSnapshot> task = firestore.collection(COLLECTION_NAME).document(id).get();

			try {
				DocumentSnapshot document = Tasks.await(task);

				if (document.exists()) {
					FirebaseGameTeam firebaseGameTeam = document.toObject(FirebaseGameTeam.class);
					firebaseGameTeam.setId(document.getId());
					return firebaseGameTeam;
				}

				throw new CompletionException(new Exception("GameTeam not found"));
			} catch (Exception e) {
				throw new CompletionException(e);
			}
		});
	}

	private CompletableFuture<Void> removeFirebaseGameTeamById(String id) {
		return CompletableFuture.supplyAsync(() -> {
			Task<Void> task = firestore.collection(COLLECTION_NAME).document(id).delete();

			try {
				Tasks.await(task);

				return null;
			} catch (Exception e) {
				throw new CompletionException(e);
			}
		});
	}

	private CompletableFuture<FirebaseGameTeam> updateFirebaseGameTeam(FirebaseGameTeam firebaseGameTeam) {
		return CompletableFuture.supplyAsync(() -> {
			Task<Void> task = firestore.collection(COLLECTION_NAME)
				.document(firebaseGameTeam.getId())
				.update(firebaseGameTeam.toMap());

			try {
				Tasks.await(task);

				return firebaseGameTeam.getId();
			} catch (Exception e) {
				throw new CompletionException(e);
			}
		}).thenCompose(this::findFirebaseGameTeamById);
	}

	private CompletableFuture<List<FirebaseGameTeam>> findAllFirebaseGameTeams() {
		return CompletableFuture.supplyAsync(() -> {
			Task<QuerySnapshot> task = firestore.collection(COLLECTION_NAME).get();

			try {
				QuerySnapshot querySnapshot = Tasks.await(task);

				return querySnapshot
					.getDocuments()
					.stream()
					.map(document -> document.toObject(FirebaseGameTeam.class))
					.collect(Collectors.toList());
			} catch (Exception e) {
				throw new CompletionException(e);
			}
		});
	}
}
