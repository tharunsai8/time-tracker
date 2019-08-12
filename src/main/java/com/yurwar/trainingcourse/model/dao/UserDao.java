package com.yurwar.trainingcourse.model.dao;

import com.yurwar.trainingcourse.model.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of generic dao to handle with user entity in database
 */
public interface UserDao extends GenericDao<User> {
    /**
     * @param username Username of user entity
     * @return Entity with given username
     */
    Optional<User> findByUsername(String username);

    /**
     * Find pageable users in database
     *
     * @param page page of users list
     * @param size size of page of users list
     * @return pageable list of users
     */
    List<User> findAllPageable(int page, int size);

    /**
     * @return number of user records
     */
    long getNumberOfRecords();
}
