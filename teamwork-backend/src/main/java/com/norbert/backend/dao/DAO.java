package com.norbert.backend.dao;


import java.util.List;

public interface DAO<T> {
    List<T> getAll();
    T save(T t);
    void deleteById(Long id);

    void update(T t);
}
