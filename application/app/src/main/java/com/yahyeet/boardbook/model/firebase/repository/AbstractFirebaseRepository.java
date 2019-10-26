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

/**
 * Abstract Firebase Firestore implementation of a repository
 *
 * @param <TModel>
 */
public abstract class AbstractFirebaseRepository<TModel extends AbstractEntity> implements IRepository<TModel> {
	private FirebaseFirestore firestore;
	private String collectionNamePrefix = "";

	private Map<String, AbstractFirebaseEntity<TModel>> cache = new HashMap<>();
	private List<IRepositoryListener<TModel>> listeners = new ArrayList<>();

	public AbstractFirebaseRepository(FirebaseFirestore firestore) {
		this.firestore = firestore;
	}

	public abstract AbstractFirebaseEntity<TModel> fromModelEntityToFirebaseEntity(TModel entity);

	public abstract AbstractFirebaseEntity<TModel> fromDocumentToFirebaseEntity(DocumentSnapshot document);

	@Override
	public CompletableFuture<TModel> save(TModel entity) {
		if (entity.getId() == null) {
			CompletableFuture<AbstractFirebaseEntity<TModel>> futureFirebaseEntity =
				createFirebaseEntity(fromModelEntityToFirebaseEntity(entity));

			CompletableFuture<Void> futureOnSave = futureFirebaseEntity
				.thenCompose(firebaseEntity -> afterSave(entity, firebaseEntity));

			return futureFirebaseEntity
				.thenCombine(futureOnSave, ((firebaseEntity, nothing) -> firebaseEntity))
				.thenApply(AbstractFirebaseEntity::toModelType)
				.thenApply(createdEntity -> {
					notifyListenersOnCreate(createdEntity);

					return createdEntity;
				});
		} else {
			return findFirebaseEntityById(entity.getId()).thenCompose(firebaseEntity -> {
					CompletableFuture<AbstractFirebaseEntity<TModel>> futureFirebaseEntity =
						updateFirebaseEntity(fromModelEntityToFirebaseEntity(entity));

					CompletableFuture<Void> futureOnSave = futureFirebaseEntity
						.thenCompose(fbEntity -> afterSave(entity, fbEntity));

					return futureFirebaseEntity
						.thenCombine(futureOnSave, ((fbEntity, nothing) -> fbEntity))
						.thenApply(AbstractFirebaseEntity::toModelType)
						.thenApply(createdEntity -> {
							notifyListenersOnUpdate(createdEntity);

							return createdEntity;
						});
				}
			).exceptionally(e -> {
				try {
					CompletableFuture<AbstractFirebaseEntity<TModel>> futureFirebaseEntity =
						createFirebaseEntityWithId(fromModelEntityToFirebaseEntity(entity));

					CompletableFuture<Void> futureOnSave = futureFirebaseEntity
						.thenCompose(fbEntity -> afterSave(entity, fbEntity));

					return futureFirebaseEntity
						.thenCombine(futureOnSave, ((fbEntity, nothing) -> fbEntity))
						.thenApply(AbstractFirebaseEntity::toModelType)
						.thenApply(createdEntity -> {
							notifyListenersOnCreate(createdEntity);

							return createdEntity;
						})
						.get();
				} catch (ExecutionException | InterruptedException error) {
					throw new CompletionException(error);
				}
			});
		}
	}

	@Override
	public CompletableFuture<TModel> find(String id) {
		return findFirebaseEntityById(id).thenApply(AbstractFirebaseEntity::toModelType);
	}

	@Override
	public CompletableFuture<Void> delete(String id) {
		return removeFirebaseEntityById(id)
			.thenCompose(nothing -> afterDelete(id))
			.thenApply(nothing -> {
				notifyListenersOnDelete(id);

				return null;
			});
	}

	@Override
	public CompletableFuture<List<TModel>> all() {
		return findAllFirebaseEntities().thenApplyAsync(firebaseEntities ->
			firebaseEntities
				.stream()
				.map(AbstractFirebaseEntity::toModelType)
				.collect(Collectors.toList())
		);
	}

	@Override
	public void addListener(IRepositoryListener<TModel> listener) {
		listeners.add(listener);
	}

	@Override
	public void removeListener(IRepositoryListener<TModel> listener) {
		listeners.remove(listener);
	}

	private void notifyListenersOnCreate(TModel entity) {
		for (IRepositoryListener<TModel> listener : listeners) {
			listener.onCreate(entity);
		}
	}

	private void notifyListenersOnUpdate(TModel entity) {
		for (IRepositoryListener<TModel> listener : listeners) {
			listener.onUpdate(entity);
		}
	}

	private void notifyListenersOnDelete(String id) {
		for (IRepositoryListener<TModel> listener : listeners) {
			listener.onDelete(id);
		}
	}

	/**
	 * Function that is run after an entity is saved
	 *
	 * @param entity      Model entity that was saved
	 * @param savedEntity Firebase entity that was saved
	 * @return A completable future that when resolved denotes that the afterSave hook has completed
	 */
	public CompletableFuture<Void> afterSave(TModel entity, AbstractFirebaseEntity<TModel> savedEntity) {
		return CompletableFuture.completedFuture(null);
	}

	/**
	 * Function that is run after an entity is removed
	 *
	 * @param id The id of deleted entity
	 * @return A completable future that when resolved denotes that the afterDelete hook has completed
	 */
	public CompletableFuture<Void> afterDelete(String id) {
		return CompletableFuture.completedFuture(null);
	}

	/**
	 * @return The full Firebase Firestore collection name
	 */
	String getFullCollectionName() {
		return collectionNamePrefix + "_" + getCollectionName();
	}

	/**
	 * @return The base collection name
	 */
	public abstract String getCollectionName();

	/**
	 * Creates a new entity in Firebase Firestore
	 *
	 * @param firebaseEntity Entity to be stored
	 * @return A completable future that resolves to the created Firebase entity
	 */
	private CompletableFuture<AbstractFirebaseEntity<TModel>> createFirebaseEntity(AbstractFirebaseEntity<TModel> firebaseEntity) {
		return CompletableFuture.supplyAsync(() -> {
			Task<DocumentReference> task = firestore.collection(getFullCollectionName())
				.add(firebaseEntity.toMap());

			try {
				DocumentReference documentReference = Tasks.await(task);

				return documentReference.getId();
			} catch (Exception e) {
				throw new CompletionException(e);
			}
		}).thenCompose(this::findFirebaseEntityById).thenApply(fbEntity -> {
			cache.put(fbEntity.getId(), fbEntity);

			return fbEntity;
		});
	}

	/**
	 * Creates a new entity in Firebase Firestore with a preset id
	 *
	 * @param firebaseEntity Entity to be stored
	 * @return A completable future that resolves to the created Firebase entity
	 */
	private CompletableFuture<AbstractFirebaseEntity<TModel>> createFirebaseEntityWithId(AbstractFirebaseEntity<TModel> firebaseEntity) {
		return CompletableFuture.supplyAsync(() -> {
			Task<Void> task = firestore.collection(getFullCollectionName())
				.document(firebaseEntity.getId()).set(firebaseEntity.toMap());

			try {
				Tasks.await(task);

				return firebaseEntity.getId();
			} catch (Exception e) {
				throw new CompletionException(e);
			}
		}).thenCompose(this::findFirebaseEntityById).thenApply(fbEntity -> {
			cache.put(fbEntity.getId(), fbEntity);

			return fbEntity;
		});
	}

	/**
	 * Finds a Firebase entity by id in Firebase Firestore
	 *
	 * @param id Entity id
	 * @return A completable future that resolves to the found Firebase entity. If the entity isn't
	 * found it throws an exception
	 */
	private CompletableFuture<AbstractFirebaseEntity<TModel>> findFirebaseEntityById(String id) {
		AbstractFirebaseEntity<TModel> cachedEntity = cache.get(id);
		if (cachedEntity != null) {
			return CompletableFuture.completedFuture(cachedEntity);
		}

		return CompletableFuture.supplyAsync(() -> {
			Task<DocumentSnapshot> task = firestore.collection(getFullCollectionName()).document(id).get();

			try {
				DocumentSnapshot document = Tasks.await(task);

				if (document.exists()) {
					AbstractFirebaseEntity<TModel> entity = fromDocumentToFirebaseEntity(document);
					cache.put(entity.getId(), entity);
					return entity;
				}

				throw new CompletionException(new Exception("Entity not found"));
			} catch (Exception e) {
				throw new CompletionException(e);
			}
		});
	}

	/**
	 * Removes a Firebase entity from Firebase Firestore
	 *
	 * @param id Entity id
	 * @return A completable future that when resolves denotes that the Firebase entity was removed.
	 * If no such entity is found it throws an exception
	 */
	private CompletableFuture<Void> removeFirebaseEntityById(String id) {
		return CompletableFuture.supplyAsync(() -> {
			Task<Void> task = firestore.collection(getFullCollectionName()).document(id).delete();

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

	/**
	 * Updates the fields of a Firebase entity in Firebase Firestore
	 *
	 * @param firebaseEntity The entity with updated values
	 * @return A completable future that resolves to the updates Firebase entity, if the opration is
	 * not successful it throws an exception
	 */
	private CompletableFuture<AbstractFirebaseEntity<TModel>> updateFirebaseEntity(AbstractFirebaseEntity<TModel> firebaseEntity) {
		return CompletableFuture.supplyAsync(() -> {
			Task<Void> task = firestore.collection(getFullCollectionName())
				.document(firebaseEntity.getId())
				.update(firebaseEntity.toMap());

			try {
				Tasks.await(task);

				return firebaseEntity.getId();
			} catch (Exception e) {
				throw new CompletionException(e);
			}
		}).thenApply(id -> {
			cache.put(id, firebaseEntity);

			return id;
		}).thenCompose(this::findFirebaseEntityById);
	}

	/**
	 * Finds all Firebase entities in Firebase Firestore
	 *
	 * @return A completable future that resolves to a list of all Firebase entities, throws an
	 * exception if the operation is not successful
	 */
	private CompletableFuture<List<AbstractFirebaseEntity<TModel>>> findAllFirebaseEntities() {
		return CompletableFuture.supplyAsync(() -> {
			Task<QuerySnapshot> task = firestore.collection(getFullCollectionName()).get();

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
		});
	}

	public void setCollectionNamePrefix(String collectionNamePrefix) {
		this.collectionNamePrefix = collectionNamePrefix;
	}

	String getCollectionNamePrefix() {
		return collectionNamePrefix;
	}

	protected FirebaseFirestore getFirestore() {
		return firestore;
	}

	Map<String, AbstractFirebaseEntity<TModel>> getCache() {
		return cache;
	}
}
