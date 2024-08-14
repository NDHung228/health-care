package likelion.edu.vn.health_care.service;

import java.util.Optional;

public interface BaseService<T> {
    T create(T t);
    T update(T t);
    void delete(T t);
    Iterable<T> findAll();
    Optional<T> findById(Long id);
}
