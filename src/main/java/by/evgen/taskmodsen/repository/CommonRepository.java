package by.evgen.taskmodsen.repository;

import by.evgen.taskmodsen.entity.Model;

import java.util.List;

public interface CommonRepository<T extends Model> {

    T findById(Long id);

    List<T> findAll();

    void save(T model);

    void update(T model);

    void deleteById(Long id);
}
