package com.yurwar.trainingcourse.model.dao.impl;

import com.yurwar.trainingcourse.model.dao.UserDao;
import com.yurwar.trainingcourse.model.dao.impl.mapper.ActivityMapper;
import com.yurwar.trainingcourse.model.dao.impl.mapper.ActivityRequestMapper;
import com.yurwar.trainingcourse.model.dao.impl.mapper.UserMapper;
import com.yurwar.trainingcourse.model.entity.Activity;
import com.yurwar.trainingcourse.model.entity.ActivityRequest;
import com.yurwar.trainingcourse.model.entity.Authority;
import com.yurwar.trainingcourse.model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.Duration;
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
            try (PreparedStatement authorityPS = connection.prepareStatement("insert into user_authorities (user_id, authorities) values (?, ?)")) {
                for (Authority authority : entity.getAuthorities()) {
                    authorityPS.setLong(1, entity.getId());
                    authorityPS.setString(2, authority.name());
                    authorityPS.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        log.debug("Try to find user by username in dao");
        try (PreparedStatement ps =
                     connection.prepareStatement("select * from users left join user_authorities on users.id = user_authorities.user_id left join users_activities on users.id = users_activities.user_id left join activities on users_activities.activity_id = activities.id left join activity_requests on users.id = activity_requests.user_id  where username = ?")) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();


            Map<Long, User> userMap = extractMappedUsers(rs);
            return userMap.values().stream().findAny();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> findById(long id) {
        try (PreparedStatement ps =
                     connection.prepareStatement("select * from users left join user_authorities on users.id = user_authorities.user_id left join users_activities on users.id = users_activities.user_id left join activities on users_activities.activity_id = activities.id left join activity_requests on users.id = activity_requests.user_id where users.id = ?")) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            Map<Long, User> userMap = extractMappedUsers(rs);
            return Optional.ofNullable(userMap.get(id));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> findAll() {
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery("select * from users left join user_authorities ua on users.id = ua.user_id left join users_activities u on users.id = u.user_id left join activities a on u.activity_id = a.id left join activity_requests ar on users.id = ar.user_id");

            Map<Long, User> userMap = extractMappedUsers(rs);
            return new ArrayList<>(userMap.values());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User entity) {
        try (PreparedStatement userPS = connection.prepareStatement("update users set first_name = ?, last_name = ?, password = ?, username = ? where id = ?")) {
            userPS.setString(1, entity.getFirstName());
            userPS.setString(2, entity.getLastName());
            userPS.setString(3, entity.getPassword());
            userPS.setString(4, entity.getUsername());
            userPS.setLong(5, entity.getId());
            userPS.executeUpdate();

            try (PreparedStatement authorityDeletePS = connection.prepareStatement("delete from user_authorities where user_id = ?")) {
                authorityDeletePS.setLong(1, entity.getId());
                authorityDeletePS.executeUpdate();
            }
            try (PreparedStatement authorityInsertPS = connection.prepareStatement("insert into user_authorities values (?, ?)")) {
                for (Authority authority : entity.getAuthorities()) {
                    authorityInsertPS.setLong(1, entity.getId());
                    authorityInsertPS.setString(2, authority.name());
                    authorityInsertPS.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

    private Map<Long, User> extractMappedUsers(ResultSet rs) throws SQLException {
        Map<Long, User> userMap = new HashMap<>();
        Map<Long, Activity> activityMap = new HashMap<>();
        Map<Long, ActivityRequest> activityRequestMap = new HashMap<>();

        UserMapper userMapper = new UserMapper();
        ActivityMapper activityMapper = new ActivityMapper();
        ActivityRequestMapper activityRequestMapper = new ActivityRequestMapper();

        while (rs.next()) {
            User user = userMapper.extractFromResultSet(rs);
            Activity activity = activityMapper.extractFromResultSet(rs);
            ActivityRequest activityRequest = activityRequestMapper.extractFromResultSet(rs);
            activityRequest.setActivity(activity);
            activityRequest.setUser(user);
            Authority authority = Authority.valueOf(rs.getString(7));

            user = userMapper.makeUnique(userMap, user);
            //fixme check multiply activities
            activity = activityMapper.makeUnique(activityMap, activity);
            activityRequest = activityRequestMapper.makeUnique(activityRequestMap, activityRequest);

            user.getActivities().add(activity);
            user.getActivityRequests().add(activityRequest);
            user.getAuthorities().add(authority);
        }
        return userMap;
    }
}
