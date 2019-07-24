package com.yurwar.trainingcourse.model.service;

import com.yurwar.trainingcourse.model.dao.DaoFactory;
import com.yurwar.trainingcourse.model.dao.UserDao;
import com.yurwar.trainingcourse.model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UserService {
    private DaoFactory daoFactory = DaoFactory.getInstance();
    private static final Logger log = LogManager.getLogger();

    public Optional<User> getUserByUsername(String username) {
        try (UserDao userDao = daoFactory.createUserDao()) {
            log.info("Trying to get user by username: " + username);
            return userDao.findByUsername(username);
        } catch (Exception e) {
            log.warn("Can not find user with username: " + username);
            return Optional.empty();
        }
    }

    public boolean registerUser(User user) {
        try (UserDao userDao = daoFactory.createUserDao()) {
            log.info("Trying to create new user: " + user);
            userDao.create(user);
            return true;
        } catch (Exception e) {
            log.warn("Can not register user: " + user);
            return false;
        }
    }

    public List<User> getAllUsers() {
        try (UserDao userDao = daoFactory.createUserDao()) {
            return userDao.findAll();
        } catch (Exception e) {
            log.warn("Can not get all users");
            return Collections.emptyList();
        }
    }

    public void deleteUserById(long id) {
        try (UserDao userDao = daoFactory.createUserDao()) {
            userDao.delete(id);
        } catch (Exception e) {
            log.warn("Can not delete user");
        }
    }
}
