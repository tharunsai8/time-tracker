package com.yurwar.trainingcourse.model.dao.impl;

import com.yurwar.trainingcourse.model.dao.ActivityDao;
import com.yurwar.trainingcourse.model.entity.Activity;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class JDBCActivityDao implements ActivityDao {
    private final Connection connection;
    private ResourceBundle resourceBundle = ResourceBundle.getBundle("database");

    JDBCActivityDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Activity entity) {

    }

    @Override
    public Optional<Activity> findById(long id) {
        return Optional.empty();
    }

    @Override
    public List<Activity> findAll() {
        List<Activity> activities = new ArrayList<>();
        try (Statement st = connection.createStatement()) {
            st.executeQuery("SELECT * FROM activities");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void update(Activity entity) {

    }

    @Override
    public void delete(long id) {

    }

    @Override
    public void close() throws Exception {

    }
}
