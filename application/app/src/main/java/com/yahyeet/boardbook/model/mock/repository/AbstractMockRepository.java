package com.yahyeet.boardbook.model.mock.repository;

import com.yahyeet.boardbook.model.entity.AbstractEntity;
import com.yahyeet.boardbook.model.repository.IRepository;
import com.yahyeet.boardbook.model.repository.IRepositoryListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

public class AbstractMockRepository<T extends AbstractEntity> implements IRepository<T> {

    protected List<T> mockDatabase = new ArrayList<>();
    protected int idCount = 0;

    @Override
    public CompletableFuture<T> find(String id) {
        return CompletableFuture.supplyAsync(() -> {

            for (T t : mockDatabase) {
                if (t.getId().equals(id))
                    return t;
            }

            throw new CompletionException(new Exception("No user found"));
        });
    }

    @Override
    public CompletableFuture<T> save(T entity) {
        Function<T, T> create = T -> {
            entity.setId(Integer.toString(idCount));
            idCount++;
            mockDatabase.add(entity);
            return entity;
        };

        return CompletableFuture.supplyAsync(() -> {
            if (entity.getId() == null) {
                return create.apply(entity);
            } else {
                AtomicBoolean found = new AtomicBoolean(false);
                mockDatabase.forEach(m -> {
                    if (m.equals(entity)) {
                        int index = mockDatabase.indexOf(m);
                        mockDatabase.set(index, entity);
                        found.set(true);
                    }
                });

                if (!found.get()) {
                    return create.apply(entity);
                }
            }

            throw new CompletionException(new IllegalStateException("The state was illegal!"));
        });
    }

    @Override
    public CompletableFuture<Void> delete(T entity) {
        return CompletableFuture.supplyAsync(() -> {
            for (int i = 0; i < mockDatabase.size(); i++) {
                if (entity.getId().equals(mockDatabase.get(i).getId())) {
                    mockDatabase.remove(i);
                    return null;
                }
            }

            throw new CompletionException(new Exception("Not a user in database"));
        });
    }

    @Override
    public CompletableFuture<List<T>> all() {
        return CompletableFuture.supplyAsync(() -> mockDatabase);
    }

    @Override
    public void addListener(IRepositoryListener listener) {

    }

    @Override
    public void removeListener(IRepositoryListener listener) {

    }
}
