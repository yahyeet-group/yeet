package com.yahyeet.boardbook.model.handler;

import com.yahyeet.boardbook.model.entity.AbstractEntity;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class EntityHandler<T extends AbstractEntity> {

	public abstract CompletableFuture<T> find(String id);

	public abstract CompletableFuture<T> save(T entity);

	public abstract CompletableFuture<List<T>> all();
}
