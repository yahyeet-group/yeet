package com.yahyeet.boardbook.model.firebase.repository;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.yahyeet.boardbook.model.entity.AbstractEntity;
import com.yahyeet.boardbook.model.repository.IRepository;
import com.yahyeet.boardbook.model.repository.IRepositoryListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public abstract class AbstractFirebaseRepository<TModel extends AbstractEntity> implements IRepository<TModel> {
	private FirebaseFirestore firestore;

	private Map<String, FirebaseEntity<TModel>> cache = new HashMap();
	private List<IRepositoryListener<TModel>> listeners = new ArrayList<>();

	public AbstractFirebaseRepository(FirebaseFirestore firestore) {
		this.firestore = firestore;
	}

	@Override
	public CompletableFuture<TModel> save(TModel entity) {
		if (entity.getId() == null) {
			return createFirebaseEntity(FirebaseEntity.fromModelType(entity)).thenApply(firebaseEntity -> {
				cache.put(firebaseEntity.getId(), firebaseEntity);

				return firebaseEntity;
			}).thenApply(FirebaseEntity::toModelType);
		} else {
			return findFirebaseEntityById(entity.getId()).thenCompose(firebaseEntity ->
				updateFirebaseEntity(firebaseEntity).thenApply(fbEntity -> {
					cache.put(fbEntity.getId(), fbEntity);

					return fbEntity;
				}).thenApply(FirebaseEntity::toModelType)
			).exceptionally(e -> {
				try {
					return createFirebaseEntity(FirebaseEntity.fromModelType(entity)).thenApply(firebaseEntity -> {
						cache.put(firebaseEntity.getId(), firebaseEntity);

						return firebaseEntity;
					}).thenApply(FirebaseEntity::toModelType).get();
				} catch (ExecutionException | InterruptedException error) {
					throw new CompletionException(error);
				}
			});
		}
	}

	@Override
	public CompletableFuture<TModel> find(String id) {
		return findFirebaseEntityById(id).thenApply(FirebaseEntity::toModelType);
	}

	@Override
	public CompletableFuture<Void> delete(TModel entity) {
		return removeFirebaseEntityById(entity.getId());
	}

	@Override
	public CompletableFuture<List<TModel>> all() {
		return findAllFirebaseEntities().thenApplyAsync(firebaseEntities ->
			firebaseEntities
				.stream()
				.map(firebaseEntity -> firebaseEntity.toModelType())
				.collect(Collectors.toList())
		);
	}

	@Override
	public void addListener(IRepositoryListener<TModel> listener) {

	}

	@Override
	public void removeListener(IRepositoryListener<TModel> listener) {

	}

	public abstract String getCollectionName();

	private CompletableFuture<FirebaseEntity<TModel>> createFirebaseEntity(FirebaseEntity<TModel> firebaseEntity) {
		return CompletableFuture.supplyAsync(() -> {
			Task<DocumentReference> task = firestore.collection(getCollectionName())
				.add(firebaseEntity.toMap());

			try {
				DocumentReference documentReference = Tasks.await(task);

				return documentReference.getId();
			} catch (Exception e) {
				throw new CompletionException(e);
			}
		}).thenCompose(this::findFirebaseEntityById);
	}

	private CompletableFuture<FirebaseEntity<TModel>> findFirebaseEntityById(String id) {
		FirebaseEntity<TModel> cachedEntity = cache.get(id);
		if (cachedEntity != null) {
			return CompletableFuture.completedFuture(cachedEntity);
		}

		return CompletableFuture.supplyAsync(() -> {
			Task<DocumentSnapshot> task = firestore.collection(getCollectionName()).document(id).get();

			try {
				DocumentSnapshot document = Tasks.await(task);

				if (document.exists()) {
					return FirebaseEntity.fromDocument(document);
				}

				throw new CompletionException(new Exception("Game not found"));
			} catch (Exception e) {
				throw new CompletionException(e);
			}
		});
	}

	private CompletableFuture<Void> removeFirebaseEntityById(String id) {
		return CompletableFuture.supplyAsync(() -> {
			Task<Void> task = firestore.collection(getCollectionName()).document(id).delete();

			try {
				Tasks.await(task);

				return null;
			} catch (Exception e) {
				throw new CompletionException(e);
			}
		}).thenApply(nothing -> {
			cache.remove(id);

			return null;
		});
	}

	private CompletableFuture<FirebaseEntity<TModel>> updateFirebaseEntity(FirebaseEntity firebaseEntity) {
		return CompletableFuture.supplyAsync(() -> {
			Task<Void> task = firestore.collection(getCollectionName())
				.document(firebaseEntity.getId())
				.update(firebaseEntity.toMap());

			try {
				Tasks.await(task);

				return firebaseEntity.getId();
			} catch (Exception e) {
				throw new CompletionException(e);
			}
		}).thenApply(id -> {
			FirebaseEntity<TModel> cachedEntity = cache.get(firebaseEntity.getId());
			if (cachedEntity != null) {
				cachedEntity.copyFields(firebaseEntity);
			}

			return id;
		}).thenCompose(this::findFirebaseEntityById);
	}

	private CompletableFuture<List<FirebaseEntity<TModel>>> findAllFirebaseEntities() {
		return CompletableFuture.supplyAsync(() -> {
			Task<QuerySnapshot> task = firestore.collection(getCollectionName()).get();

			try {
				QuerySnapshot querySnapshot = Tasks.await(task);

				return querySnapshot
					.getDocuments()
					.stream()
					.map(FirebaseEntity::<TModel>fromDocument)
					.collect(Collectors.toList());
			} catch (Exception e) {
				throw new CompletionException(e);
			}
		});
	}
}