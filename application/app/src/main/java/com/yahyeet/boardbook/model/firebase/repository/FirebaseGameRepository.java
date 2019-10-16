package com.yahyeet.boardbook.model.firebase.repository;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.model.entity.GameTeam;
import com.yahyeet.boardbook.model.repository.IGameRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;

public class FirebaseGameRepository implements IGameRepository {
	private FirebaseFirestore firestore;

	private FirebaseGameRoleRepository firebaseGameRoleRepository;
	private FirebaseGameTeamRepository firebaseGameTeamRepository;

	public static final String COLLECTION_NAME = "games";

	public FirebaseGameRepository(FirebaseFirestore firestore) {
		this.firestore = firestore;
		firebaseGameRoleRepository = new FirebaseGameRoleRepository(firestore);
		firebaseGameTeamRepository = new FirebaseGameTeamRepository(firestore);
	}

	@Override
	public CompletableFuture<Game> create(Game game) {
		List<CompletableFuture<GameTeam>> completableFutures = game
			.getTeams()
			.stream()
			// TODO: save
			.map(team -> firebaseGameTeamRepository.create(team))
			.collect(Collectors.toList());
		CompletableFuture<Void> allFutures = CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0]));
		return allFutures.thenApply(future -> completableFutures.stream().map(CompletableFuture::join).collect(Collectors.toList()))
		.thenCompose(gameTeams -> {
			game.setTeams(gameTeams);
			return createFirebaseGame(FirebaseGame.fromGame(game));
		}).thenApplyAsync(FirebaseGame::toGame);
	}

	@Override
	public CompletableFuture<Game> find(String id) {
		return findFirebaseGameById(id).thenApplyAsync(FirebaseGame::toGame);
	}

	@Override
	public CompletableFuture<Game> update(Game game) {
		return updateFirebaseGame(FirebaseGame.fromGame(game)).thenApplyAsync(FirebaseGame::toGame);
	}

	@Override
	public CompletableFuture<Void> remove(Game game) {
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
		return CompletableFuture.supplyAsync(() -> {
			Task<DocumentSnapshot> task = firestore.collection(COLLECTION_NAME).document(id).get();

			try {
				DocumentSnapshot document = Tasks.await(task);

				if (document.exists()) {
					return documentToFirebaseGame(document);
				}

				throw new CompletionException(new Exception("Game not found"));
			} catch (Exception e) {
				throw new CompletionException(e);
			}
		});
	}

	@SuppressWarnings("checked")
	private FirebaseGame documentToFirebaseGame(DocumentSnapshot document) {
		Map<String, Object> data = document.getData();

		FirebaseGame game = new FirebaseGame();

		game.setId(document.getId());

		assert data != null;
		if (data.containsKey("description")) {
			game.setDescription((String) data.get("description"));
		}

		if (data.containsKey("difficulty")) {
			game.setDifficulty(((Long) Objects.requireNonNull(data.get("difficulty"))).intValue());
		}

		if (data.containsKey("maxPlayers")) {
			game.setMaxPlayers(((Long) Objects.requireNonNull(data.get("maxPlayers"))).intValue());
		}

		if (data.containsKey("minPlayers")) {
			game.setMinPlayers(((Long) Objects.requireNonNull(data.get("minPlayers"))).intValue());
		}

		if (data.containsKey("name")) {
			game.setName((String) data.get("name"));
		}

		return game;
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
					.map(this::documentToFirebaseGame)
					.collect(Collectors.toList());
			} catch (Exception e) {
				throw new CompletionException(e);
			}
		});
	}
}
