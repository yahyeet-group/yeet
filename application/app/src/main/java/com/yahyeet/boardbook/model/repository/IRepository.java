package com.yahyeet.boardbook.model.repository;

import com.yahyeet.boardbook.model.entity.AbstractEntity;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IRepository<T extends AbstractEntity> {
    CompletableFuture<T> create(T entity);

    CompletableFuture<T> find(String id);

    CompletableFuture<T> update(T entity);

    CompletableFuture<Void> remove(T entity);

    CompletableFuture<List<T>> all();
}
