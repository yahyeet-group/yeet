package com.yahyeet.boardbook.model.repository;

public abstract class RepositoryResultListener<T> {
    abstract public void onError(Exception e);

    abstract public void onSuccess(T result);
}
