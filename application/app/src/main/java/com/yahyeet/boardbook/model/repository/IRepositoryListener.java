package com.yahyeet.boardbook.model.repository;

/**
 * Interface that defines all the methods a repository listener must implement
 */
public interface IRepositoryListener<T> {
	/**
	 * Called when an entity is created
	 *
	 * @param entity The created entity
	 */
	void onCreate(T entity);

	/**
	 * Called when an entity is updated
	 *
	 * @param entity The updated entity
	 */
	void onUpdate(T entity);

	/**
	 * Called when an entity is deleted
	 *
	 * @param entity The deleted entity
	 */
	void onDelete(T entity);
}
