package com.yahyeet.boardbook.model.firebase.repository;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.yahyeet.boardbook.model.entity.GameRole;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;

public class FirebaseGameRoleRepository {
	private FirebaseFirestore firestore;

	public static final String COLLECTION_NAME = "game_roles";

	public FirebaseGameRoleRepository(FirebaseFirestore firestore) {
		this.firestore = firestore;
	}

	public CompletableFuture<GameRole> create(GameRole gameRoleRole) {
		return createFirebaseGameRole(FirebaseGameRole.fromGameRole(gameRoleRole)).thenApplyAsync(FirebaseGameRole::toGameRole);

	}

	public CompletableFuture<GameRole> find(String id) {
		return findFirebaseGameRoleById(id).thenApplyAsync(FirebaseGameRole::toGameRole);
	}


	public CompletableFuture<GameRole> update(GameRole gameRoleRole) {
		return updateFirebaseGameRole(FirebaseGameRole.fromGameRole(gameRoleRole)).thenApplyAsync(FirebaseGameRole::toGameRole);
	}


	public CompletableFuture<Void> remove(GameRole gameRoleRole) {
		return removeFirebaseGameRoleById(gameRoleRole.getId());
	}

	public CompletableFuture<List<GameRole>> all() {
		return findAllFirebaseGameRoles().thenApplyAsync(firebaseUsers ->
			firebaseUsers
				.stream()
				.map(FirebaseGameRole::toGameRole)
				.collect(Collectors.toList())
		);
	}


	private CompletableFuture<FirebaseGameRole> createFirebaseGameRole(FirebaseGameRole firebaseGameRole) {
		return CompletableFuture.supplyAsync(() -> {
			Task<DocumentReference> task = firestore.collection(COLLECTION_NAME)
				.add(firebaseGameRole.toMap());

			try {
				DocumentReference documentReference = Tasks.await(task);

				return documentReference.getId();
			} catch (Exception e) {
				throw new CompletionException(e);
			}
		}).thenCompose(this::findFirebaseGameRoleById);
	}

	private CompletableFuture<FirebaseGameRole> findFirebaseGameRoleById(String id) {
		return CompletableFuture.supplyAsync(() -> {
			Task<DocumentSnapshot> task = firestore.collection(COLLECTION_NAME).document(id).get();

			try {
				DocumentSnapshot document = Tasks.await(task);

				if (document.exists()) {
					FirebaseGameRole firebaseGameRole = document.toObject(FirebaseGameRole.class);
					firebaseGameRole.setId(document.getId());
					return firebaseGameRole;
				}

				throw new CompletionException(new Exception("GameRole not found"));
			} catch (Exception e) {
				throw new CompletionException(e);
			}
		});
	}

	private CompletableFuture<Void> removeFirebaseGameRoleById(String id) {
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

	private CompletableFuture<FirebaseGameRole> updateFirebaseGameRole(FirebaseGameRole firebaseGameRole) {
		return CompletableFuture.supplyAsync(() -> {
			Task<Void> task = firestore.collection(COLLECTION_NAME)
				.document(firebaseGameRole.getId())
				.update(firebaseGameRole.toMap());

			try {
				Tasks.await(task);

				return firebaseGameRole.getId();
			} catch (Exception e) {
				throw new CompletionException(e);
			}
		}).thenCompose(this::findFirebaseGameRoleById);
	}

	private CompletableFuture<List<FirebaseGameRole>> findAllFirebaseGameRoles() {
		return CompletableFuture.supplyAsync(() -> {
			Task<QuerySnapshot> task = firestore.collection(COLLECTION_NAME).get();

			try {
				QuerySnapshot querySnapshot = Tasks.await(task);

				return querySnapshot
					.getDocuments()
					.stream()
					.map(document -> document.toObject(FirebaseGameRole.class))
					.collect(Collectors.toList());
			} catch (Exception e) {
				throw new CompletionException(e);
			}
		});
	}
}
