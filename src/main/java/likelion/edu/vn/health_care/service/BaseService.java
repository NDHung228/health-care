package likelion.edu.vn.health_care.service;

import likelion.edu.vn.health_care.model.dto.ResultPaginationDTO;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BaseService<T> {
    T create(T t);
    T update(T t);
    void delete(T t);
    Iterable<T> findAll();
    Optional<T> findById(Integer id);
    ResultPaginationDTO handleGetAll(Pageable pageable);
}
