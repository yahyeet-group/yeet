package com.yahyeet.boardbook.model.repository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IRepository<T> {
    CompletableFuture<T> find(String id);

    CompletableFuture<T> save(T entity);

    CompletableFuture<Void> delete(T entity);

    CompletableFuture<List<T>> all();

    void addListener(IRepositoryListener<T> listener);

    void removeListener(IRepositoryListener<T> listener);
}
