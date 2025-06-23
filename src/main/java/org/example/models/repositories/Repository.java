package org.example.models.repositories;

import java.util.List;

public interface Repository<T> {
    List<T> getAll();         // Получить все объекты типа T
    void create(T entity);    // Создать объект типа T
    void update(T entity);    // Обновить объект типа T
    void delete(int id);      // Удалить объект по id
}
