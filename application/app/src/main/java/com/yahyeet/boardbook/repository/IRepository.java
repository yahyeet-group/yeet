package com.yahyeet.boardbook.repository;

public interface IRepository<T> {
    void Add(T entity);

    void Find(String id);

    void Remove(T entity);

    void Update(T entity);

}
