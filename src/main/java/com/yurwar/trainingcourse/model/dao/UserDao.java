package com.yurwar.trainingcourse.model.dao;

import com.yurwar.trainingcourse.model.entity.User;

import java.util.Optional;

public interface UserDao extends GenericDao<User> {
    Optional<User> findByUsername(String username);
}
