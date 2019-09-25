package com.yahyeet.boardbook.model.repository;

public interface IRepository<T> {
    void Add(T entity);

    T Find(String id);

    void Remove(T entity);

    void Update(T entity);
}
