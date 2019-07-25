package com.yurwar.trainingcourse.model.dao.impl;

import com.yurwar.trainingcourse.model.dao.UserDao;
import com.yurwar.trainingcourse.model.entity.Role;
import com.yurwar.trainingcourse.model.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class JDBCUserDao implements UserDao {
    private Connection connection;
    private ResourceBundle resourceBundle;

    public JDBCUserDao(Connection connection) {
        this.resourceBundle = ResourceBundle.getBundle("database.properties");
        this.connection = connection;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        try (PreparedStatement ps =
                connection.prepareStatement(resourceBundle.getString("query.user.find.by_username"))) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(fetchUserFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public void create(User entity) {
        try (PreparedStatement ps =
                    connection.prepareStatement(resourceBundle.getString("query.user.create"))) {
            ps.setString(1, entity.getFirstName());
            ps.setString(2, entity.getLastName());
            ps.setString(3, entity.getPassword());
            ps.setString(4, entity.getUsername());
            ps.setString(5, entity.getRole().name());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> findById(long id) {
        try (PreparedStatement ps =
                connection.prepareStatement(resourceBundle.getString("query.user.find.by_id"))) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(fetchUserFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(resourceBundle.getString("query.user.find.all"));
            while (rs.next()) {
                users.add(fetchUserFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public void update(User entity) {
        //TODO add update method
        throw new IllegalStateException();
    }

    @Override
    public void delete(long id) {
        try (PreparedStatement ps = connection.prepareStatement(resourceBundle.getString("query.user.delete"))) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws SQLException {
        connection.close();
    }


    private User fetchUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setPassword(rs.getString("password"));
        user.setUsername(rs.getString("username"));
        user.setRole(Role.valueOf(rs.getString("role")));
        return user;
    }
}
