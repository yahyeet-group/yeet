package com.yahyeet.boardbook.model.repository;

public interface IRepositoryListener<T> {
	void onCreate(T entity);

	void onUpdate(T entity);

	void onDelete(T entity);
}
