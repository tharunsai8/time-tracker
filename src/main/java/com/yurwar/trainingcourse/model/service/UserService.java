package com.yurwar.trainingcourse.model.service;

import com.yurwar.trainingcourse.model.dao.DaoConnection;
import com.yurwar.trainingcourse.model.dao.DaoFactory;
import com.yurwar.trainingcourse.model.dao.UserDao;
import com.yurwar.trainingcourse.model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UserService {
    private final DaoFactory daoFactory = DaoFactory.getInstance();
    private static final Logger log = LogManager.getLogger();

    public Optional<User> findUserByUsername(String username) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.createUserDao(connection);
            log.info("Trying to get user by username: " + username);
            return userDao.findByUsername(username);
        } catch (Exception e) {
            log.warn("Can not find user with username: " + username);
            return Optional.empty();
        }
    }

    //TODO Replace to dto
    public boolean registerUser(User user) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.createUserDao(connection);
            log.info("Trying to create new user: " + user);
            userDao.create(user);
            return true;
        } catch (Exception e) {
            log.warn("Can not register user: " + user);
            return false;
        }
    }

    public List<User> getAllUsersPageable(int page, int size) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.createUserDao(connection);
            return userDao.findAllPageable(page, size);
        } catch (Exception e) {
            log.warn("Can not get all users", e);
            return Collections.emptyList();
        }
    }

    public void deleteUserById(long id) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.createUserDao(connection);
            userDao.delete(id);
        } catch (Exception e) {
            log.warn("Can not delete user");
        }
    }

    public Optional<User> findUserById(long id) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.createUserDao(connection);
            return userDao.findById(id);
        } catch (Exception e) {
            log.warn("Can not find user with id: " + id);
            return Optional.empty();
        }
    }

    public void updateUser(User user) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.createUserDao(connection);
            userDao.update(user);
        } catch (Exception e) {
            log.warn("Can not update user " + user.getUsername(), e);
        }
    }

    public long getNumberOfRecords() {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.createUserDao(connection);
            return userDao.getNumbersOfRecords();
        } catch (Exception e) {
            log.warn("Can not get number of users", e);
        }
        return 0;
    }
}
