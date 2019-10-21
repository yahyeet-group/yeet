package com.yahyeet.boardbook.model.handler;

import com.yahyeet.boardbook.model.entity.AbstractEntity;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface EntityHandler<T extends AbstractEntity> {

	CompletableFuture<T> find(String id);

	CompletableFuture<T> save(T entity);

	CompletableFuture<List<T>> all();
}
