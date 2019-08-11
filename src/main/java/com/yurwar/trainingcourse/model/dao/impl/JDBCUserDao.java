package com.yurwar.trainingcourse.model.dao.impl;

import com.yurwar.trainingcourse.model.dao.UserDao;
import com.yurwar.trainingcourse.model.dao.impl.mapper.ActivityMapper;
import com.yurwar.trainingcourse.model.dao.impl.mapper.ActivityRequestMapper;
import com.yurwar.trainingcourse.model.dao.impl.mapper.UserMapper;
import com.yurwar.trainingcourse.model.entity.Activity;
import com.yurwar.trainingcourse.model.entity.ActivityRequest;
import com.yurwar.trainingcourse.model.entity.Authority;
import com.yurwar.trainingcourse.model.entity.User;
import com.yurwar.trainingcourse.util.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;

//TODO Add log
public class JDBCUserDao implements UserDao {
    public static final Logger log = LogManager.getLogger();
    private final Connection connection;
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("database");

    JDBCUserDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(User entity) {
        try (PreparedStatement userPS =
                     connection.prepareStatement(resourceBundle.getString("query.user.create"), Statement.RETURN_GENERATED_KEYS)) {
            userPS.setString(1, entity.getFirstName());
            userPS.setString(2, entity.getLastName());
            userPS.setString(3, entity.getPassword());
            userPS.setString(4, entity.getUsername());
            userPS.executeUpdate();
            ResultSet rs = userPS.getGeneratedKeys();

            if (rs.next()) {
                entity.setId(rs.getLong(1));
            }
            try (PreparedStatement authorityPS = connection.prepareStatement(resourceBundle.getString("query.authority.create"))) {
                for (Authority authority : entity.getAuthorities()) {
                    authorityPS.setLong(1, entity.getId());
                    authorityPS.setString(2, authority.name());
                    authorityPS.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Can not create user", e);
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        try (PreparedStatement ps = connection.prepareStatement(resourceBundle.getString("query.user.find.by_username"))) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            Map<Long, User> userMap = extractUsersFromResultSet(rs);

            return userMap.values().stream().findAny();
        } catch (SQLException e) {
            throw new DaoException("Can not find user by username", e);
        }
    }

    @Override
    public Optional<User> findById(long id) {
        try (PreparedStatement ps = connection.prepareStatement(resourceBundle.getString("query.user.find.by_id"))) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            Map<Long, User> userMap = extractUsersFromResultSet(rs);

            return userMap.values().stream().findAny();
        } catch (SQLException e) {
            throw new DaoException("Can not find user by id", e);
        }
    }

    @Override
    public List<User> findAll() {
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(resourceBundle.getString("query.user.find.all"));

            Map<Long, User> userMap = extractUsersFromResultSet(rs);

            return new ArrayList<>(userMap.values());
        } catch (SQLException e) {
            throw new DaoException("Can not find all users", e);
        }
    }

    @Override
    public List<User> findAllPageable(int page, int size) {
        try (PreparedStatement ps = connection.prepareStatement(resourceBundle.getString("query.user.find.all.pageable"))) {
            ps.setLong(1, size);
            ps.setLong(2, size * page);
            ResultSet rs = ps.executeQuery();

            Map<Long, User> userMap = extractUsersFromResultSet(rs);

            return new ArrayList<>(userMap.values());
        } catch (SQLException e) {
            throw new DaoException("Can not find all users", e);
        }
    }

    @Override
    public void update(User entity) {
        try (PreparedStatement userPS = connection.prepareStatement(resourceBundle.getString("query.user.update"))) {
            userPS.setString(1, entity.getFirstName());
            userPS.setString(2, entity.getLastName());
            userPS.setString(3, entity.getPassword());
            userPS.setString(4, entity.getUsername());
            userPS.setLong(5, entity.getId());
            userPS.executeUpdate();

            try (PreparedStatement authorityDeletePS = connection.prepareStatement(resourceBundle.getString("query.authority.delete.by_id"))) {
                authorityDeletePS.setLong(1, entity.getId());
                authorityDeletePS.executeUpdate();
            }
            try (PreparedStatement authorityInsertPS = connection.prepareStatement(resourceBundle.getString("query.authority.create"))) {
                for (Authority authority : entity.getAuthorities()) {
                    authorityInsertPS.setLong(1, entity.getId());
                    authorityInsertPS.setString(2, authority.name());
                    authorityInsertPS.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Can not update user", e);
        }
    }

    @Override
    public void delete(long id) {
        try (PreparedStatement ps = connection.prepareStatement(resourceBundle.getString("query.user.delete"))) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Can not delete user", e);
        }
    }

    @Override
    public long getNumberOfRecords() {
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(resourceBundle.getString("query.user.find.rows"));

            if (rs.next()) {
                return rs.getLong(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new DaoException("Can not get numbers of records", e);
        }
    }

    private Map<Long, User> extractUsersFromResultSet(ResultSet rs) throws SQLException {
        Map<Long, User> userMap = new LinkedHashMap<>();
        Map<Long, Activity> activityMap = new HashMap<>();
        Map<Long, ActivityRequest> activityRequestMap = new HashMap<>();

        UserMapper userMapper = new UserMapper();
        ActivityMapper activityMapper = new ActivityMapper();
        ActivityRequestMapper activityRequestMapper = new ActivityRequestMapper();

        while (rs.next()) {
            User user = userMapper.extractFromResultSet(rs);
            userMapper.makeUnique(userMap, user);
        }

        for (User user : userMap.values()) {
            try (PreparedStatement userAuthoritiesPS = connection.prepareStatement(resourceBundle.getString("query.user.join.authorities"))) {
                userAuthoritiesPS.setLong(1, user.getId());
                ResultSet userAuthoritiesResultSet = userAuthoritiesPS.executeQuery();

                while (userAuthoritiesResultSet.next()) {
                    Authority authority = Authority
                            .valueOf(userAuthoritiesResultSet.getString("user_authorities.authorities"));
                    user.getAuthorities().add(authority);
                }
            }
            try (PreparedStatement userActivitiesPS = connection.prepareStatement(resourceBundle.getString("query.user.join.activities"))) {
                userActivitiesPS.setLong(1, user.getId());
                ResultSet userActivitiesResultSet = userActivitiesPS.executeQuery();

                while (userActivitiesResultSet.next()) {
                    Activity activity = activityMapper.extractFromResultSet(userActivitiesResultSet);
                    activity = activityMapper.makeUnique(activityMap, activity);

                    if (activity.getId() != 0 && !user.getActivities().contains(activity)) {
                        user.getActivities().add(activity);
                    }
                }
            }
            try (PreparedStatement activityRequestsPS = connection.prepareStatement(resourceBundle.getString("query.user.join.activity_requests"))) {
                activityRequestsPS.setLong(1, user.getId());
                ResultSet userActivityRequestsResultSet = activityRequestsPS.executeQuery();

                while (userActivityRequestsResultSet.next()) {
                    ActivityRequest activityRequest = activityRequestMapper.extractFromResultSet(userActivityRequestsResultSet);
                    Activity activity = activityMapper.extractFromResultSet(userActivityRequestsResultSet);

                    activityRequest = activityRequestMapper.makeUnique(activityRequestMap, activityRequest);
                    activity = activityMapper.makeUnique(activityMap, activity);

                    if ((activityRequest.getId() != 0) && !user.getActivityRequests().contains(activityRequest)) {
                        activityRequest.setUser(user);
                        activityRequest.setActivity(activity);
                        user.getActivityRequests().add(activityRequest);
                    }
                }
            }
        }
        return userMap;
    }
}
