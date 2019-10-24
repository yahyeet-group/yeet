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
	private String collectionNamePrefix = "";

	private Map<String, AbstractFirebaseEntity<TModel>> cache = new HashMap();
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
				.thenApply(AbstractFirebaseEntity::toModelType);
		} else {
			return findFirebaseEntityById(entity.getId()).thenCompose(firebaseEntity -> {
					CompletableFuture<AbstractFirebaseEntity<TModel>> futureFirebaseEntity =
						updateFirebaseEntity(fromModelEntityToFirebaseEntity(entity));

					CompletableFuture<Void> futureOnSave = futureFirebaseEntity
						.thenCompose(fbEntity -> afterSave(entity, fbEntity));

					return futureFirebaseEntity
						.thenCombine(futureOnSave, ((fbEntity, nothing) -> fbEntity))
						.thenApply(AbstractFirebaseEntity::toModelType);
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

	public CompletableFuture<Void> afterSave(TModel entity, AbstractFirebaseEntity<TModel> savedEntity) {
		return CompletableFuture.completedFuture(null);
	}

	public String getFullCollectionName() {
		return collectionNamePrefix + "_" + getCollectionName();
	}

	public abstract String getCollectionName();

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
					return fromDocumentToFirebaseEntity(document);
				}

				throw new CompletionException(new Exception("Entity not found"));
			} catch (Exception e) {
				throw new CompletionException(e);
			}
		});
	}

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

	public String getCollectionNamePrefix() {
		return collectionNamePrefix;
	}

	protected FirebaseFirestore getFirestore() {
		return firestore;
	}

	protected Map<String, AbstractFirebaseEntity<TModel>> getCache() {
		return cache;
	}
}
