package org.example.models.repositories;

import java.util.List;

public interface Repository<T> {
    List<T> getAll();
    void create(T entity);
    void update(T entity);
    void delete(int id);
}
