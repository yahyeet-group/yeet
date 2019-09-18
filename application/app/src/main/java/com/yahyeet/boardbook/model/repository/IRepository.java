package com.yahyeet.boardbook.model.repository;

import java.util.List;

public interface IRepository<T> {
    void create(T entity, RepositoryResultListener<T> listener);

    void find(String id, RepositoryResultListener<T> listener);

    void update(T entity, RepositoryResultListener<T> listener);

    void remove(T entity, RepositoryResultListener<Void> listener);

    void all(RepositoryResultListener<List<T>> listener);
}
