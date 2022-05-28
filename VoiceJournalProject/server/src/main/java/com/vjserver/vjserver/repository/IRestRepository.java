package com.vjserver.vjserver.repository;

import com.vjserver.vjserver.entity.BaseEntity;

public interface IRestRepository<T extends BaseEntity> {
    T[] select();
    T select(Integer id);
    T insert(T entity);
    T update(Integer id, T entity);
    T delete(Integer id);
}
