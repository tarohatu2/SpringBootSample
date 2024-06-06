package com.example.sampleapp.demo.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.Optional;

/**
 * Base interface for Spring Data JPA Repository
 * @param <T> entity
 * @param <ID> primary id
 */
@NoRepositoryBean
public interface BaseRepository<T, ID> extends Repository<T, ID> {
    Optional<T> findById(ID id);
    <S extends T> S save(S entity);
}
