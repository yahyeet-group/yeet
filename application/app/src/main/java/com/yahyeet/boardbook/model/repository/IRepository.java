package com.yahyeet.boardbook.model.repository;

import com.yahyeet.boardbook.model.entity.AbstractEntity;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * An interface for CRUD operations all repositories should implement
 *
 * @param <T>
 */
public interface IRepository<T extends AbstractEntity> {
    /**
     * Finds an entity by an id
     *
     * @param id Entity id
     * @return A completable future that resolves to a found entity. If no entity is found it throws
     * an exception
     */
    CompletableFuture<T> find(String id);

    /**
     * Saves an entity
     *
     * @param entity The entity
     * @return A completable future that resolves to the saved entity. If the save action fails it
     * throws an exception
     */
    CompletableFuture<T> save(T entity);

    /**
     * Deletes an entity by an id
     *
     * @param id Entity id
     * @return A completable future that when successfully resolved denotes that the entity has been
     * removes. If the deletion action fails, if no entity is found or otherwise, it throws an
     * exception
     */
    CompletableFuture<Void> delete(String id);

    /**
     * Find all instances of an entity
     *
     * @return A completable future the resolves to a list of all entities. Throws an exception if
     * the entities cannot be retrieved.
     */
    CompletableFuture<List<T>> all();

    /**
     * Adds a listener
     *
     * @param listener A repository listener
     */
    void addListener(IRepositoryListener<T> listener);

    /**
     * Removes a listener
     *
     * @param listener A repository listener
     */
    void removeListener(IRepositoryListener<T> listener);
}
