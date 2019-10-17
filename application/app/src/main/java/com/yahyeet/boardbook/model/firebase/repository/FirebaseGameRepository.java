package com.yahyeet.boardbook.model.firebase.repository;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.yahyeet.boardbook.model.entity.AbstractEntity;
import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.model.entity.GameTeam;
import com.yahyeet.boardbook.model.repository.IGameRepository;
import com.yahyeet.boardbook.model.repository.IRepositoryListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class FirebaseGameRepository implements IGameRepository {

	private static final String COLLECTION_NAME = "games";

	private FirebaseFirestore firestore;

	private Map<String, FirebaseGame> gameCache = new HashMap();
	private List<IRepositoryListener<Game>> listeners = new ArrayList<>();

	public FirebaseGameRepository(FirebaseFirestore firestore) {
		this.firestore = firestore;
	}

	@Override
	public CompletableFuture<Game> save(Game game) {
		if (game.getId() == null) {
			return createFirebaseGame(FirebaseGame.fromGame(game)).thenApply(firebaseGame -> {
				gameCache.put(firebaseGame.getId(), firebaseGame);

				return firebaseGame;
			}).thenApply(FirebaseGame::toGame);
		} else {
			return findFirebaseGameById(game.getId()).thenCompose(firebaseGame ->
				updateFirebaseGame(firebaseGame).thenApply(fbGame -> {
					gameCache.put(fbGame.getId(), fbGame);

					return fbGame;
				}).thenApply(FirebaseGame::toGame)
			).exceptionally(e -> {
				try {
					return createFirebaseGame(FirebaseGame.fromGame(game)).thenApply(firebaseGame -> {
						gameCache.put(firebaseGame.getId(), firebaseGame);

						return firebaseGame;
					}).thenApply(FirebaseGame::toGame).get();
				} catch (ExecutionException | InterruptedException error) {
					throw new CompletionException(error);
				}
			});
		}
	}

	@Override
	public CompletableFuture<Game> find(String id) {
		return findFirebaseGameById(id).thenApply(FirebaseGame::toGame);
	}

	@Override
	public CompletableFuture<Void> delete(Game game) {
		return removeFirebaseGameById(game.getId());
	}

	@Override
	public CompletableFuture<List<Game>> all() {
		return findAllFirebaseGames().thenApplyAsync(firebaseUsers ->
			firebaseUsers
				.stream()
				.map(FirebaseGame::toGame)
				.collect(Collectors.toList())
		);
	}

	@Override
	public void addListener(IRepositoryListener<Game> listener) {

	}

	@Override
	public void removeListener(IRepositoryListener<Game> listener) {

	}

	private CompletableFuture<FirebaseGame> createFirebaseGame(FirebaseGame firebaseGame) {
		return CompletableFuture.supplyAsync(() -> {
			Task<DocumentReference> task = firestore.collection(COLLECTION_NAME)
				.add(firebaseGame.toMap());

			try {
				DocumentReference documentReference = Tasks.await(task);

				return documentReference.getId();
			} catch (Exception e) {
				throw new CompletionException(e);
			}
		}).thenCompose(this::findFirebaseGameById);
	}

	private CompletableFuture<FirebaseGame> findFirebaseGameById(String id) {
		FirebaseGame cachedGame = gameCache.get(id);
		if (cachedGame != null) {
			return CompletableFuture.completedFuture(cachedGame);
		}

		return CompletableFuture.supplyAsync(() -> {
			Task<DocumentSnapshot> task = firestore.collection(COLLECTION_NAME).document(id).get();

			try {
				DocumentSnapshot document = Tasks.await(task);

				if (document.exists()) {
					return document.toObject(FirebaseGame.class);
				}

				throw new CompletionException(new Exception("Game not found"));
			} catch (Exception e) {
				throw new CompletionException(e);
			}
		});
	}

	private CompletableFuture<Void> removeFirebaseGameById(String id) {
		return CompletableFuture.supplyAsync(() -> {
			Task<Void> task = firestore.collection(COLLECTION_NAME).document(id).delete();

			try {
				Tasks.await(task);

				return null;
			} catch (Exception e) {
				throw new CompletionException(e);
			}
		}).thenApply(nothing -> {
			gameCache.remove(id);

			return null;
		});
	}

	private CompletableFuture<FirebaseGame> updateFirebaseGame(FirebaseGame firebaseGame) {
		return CompletableFuture.supplyAsync(() -> {
			Task<Void> task = firestore.collection(COLLECTION_NAME)
				.document(firebaseGame.getId())
				.update(firebaseGame.toMap());

			try {
				Tasks.await(task);

				return firebaseGame.getId();
			} catch (Exception e) {
				throw new CompletionException(e);
			}
		}).thenApply(id -> {
			FirebaseGame cachedGame = gameCache.get(firebaseGame.getId());
			if (cachedGame != null) {
				cachedGame.setDescription(firebaseGame.getDescription());
				cachedGame.setDifficulty(firebaseGame.getDifficulty());
				cachedGame.setMaxPlayers(firebaseGame.getMaxPlayers());
				cachedGame.setMinPlayers(firebaseGame.getMinPlayers());
				cachedGame.setName(firebaseGame.getName());
			}

			return id;
		}).thenCompose(this::findFirebaseGameById);
	}

	private CompletableFuture<List<FirebaseGame>> findAllFirebaseGames() {
		return CompletableFuture.supplyAsync(() -> {
			Task<QuerySnapshot> task = firestore.collection(COLLECTION_NAME).get();

			try {
				QuerySnapshot querySnapshot = Tasks.await(task);

				return querySnapshot
					.getDocuments()
					.stream()
					.map(document -> document.toObject(FirebaseGame.class))
					.collect(Collectors.toList());
			} catch (Exception e) {
				throw new CompletionException(e);
			}
		});
	}
}
