package by.evgen.taskmodsen.service;

import by.evgen.taskmodsen.entity.Model;

import java.util.List;

public interface CommonService<T extends Model> {

    T findById(Long id) throws RuntimeException;

    List<T> findAll();

    void save(T model) throws RuntimeException;

    void update(T model) throws RuntimeException;

    void deleteById(Long id);
}
