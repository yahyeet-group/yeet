package com.yahyeet.boardbook.model.handler;

import com.yahyeet.boardbook.model.entity.AbstractEntity;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * A generic interface that defines the methods an entity handles should implement
 * @param <T>
 */
public interface IEntityHandler<T extends AbstractEntity> {
	/**
	 * Finds an entity by an id
	 *
	 * @param id Entity id
	 * @return A completable future that resolves to a found entity. If no entity is found it throws
	 * an exception
	 */
	CompletableFuture<T> find(String id, Map<String, Boolean> config);
	CompletableFuture<T> find(String id);

	// TODO: Javadoc for config

	/**
	 * Saves an entity
	 *
	 * @param entity The entity
	 * @return A completable future that resolves to the saved entity. If the save action fails it
	 * throws an exception
	 */
	CompletableFuture<T> save(T entity, Map<String, Boolean> config);
	CompletableFuture<T> save(T entity);

	/**
	 * Find all instances of an entity
	 *
	 * @return A completable future the resolves to a list of all entities. Throws an exception if
	 * the entities cannot be retrieved.
	 */
	CompletableFuture<List<T>> all(Map<String, Boolean> config);
	CompletableFuture<List<T>> all();
}
