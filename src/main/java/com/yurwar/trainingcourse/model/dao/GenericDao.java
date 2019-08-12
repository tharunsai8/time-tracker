package com.yurwar.trainingcourse.model.dao;

import java.util.List;
import java.util.Optional;

/**
 * Interface with CRUD operations that implemented with all dao
 *
 * @param <T> Entity with which perform operations
 */
public interface GenericDao<T> {
    /**
     * Create entity mapping in database
     *
     * @param entity Entity to be created
     */
    void create(T entity);

    /**
     * Find entity by id in database
     *
     * @param id id of entity
     * @return optional of entity
     */
    Optional<T> findById(long id);

    /**
     * Find all entities in database
     *
     * @return list with all entities
     */
    List<T> findAll();

    /**
     * Update entity in database
     *
     * @param entity Entity to be updated
     */
    void update(T entity);

    /**
     * Delete entity from database
     *
     * @param id id of entity to delete
     */
    void delete(long id);
}
