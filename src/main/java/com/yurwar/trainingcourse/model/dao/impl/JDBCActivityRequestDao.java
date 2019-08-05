package com.yurwar.trainingcourse.model.dao.impl;

import com.yurwar.trainingcourse.model.dao.ActivityRequestDao;
import com.yurwar.trainingcourse.model.dao.impl.mapper.ActivityMapper;
import com.yurwar.trainingcourse.model.dao.impl.mapper.ActivityRequestMapper;
import com.yurwar.trainingcourse.model.dao.impl.mapper.UserMapper;
import com.yurwar.trainingcourse.model.entity.Activity;
import com.yurwar.trainingcourse.model.entity.ActivityRequest;
import com.yurwar.trainingcourse.model.entity.Authority;
import com.yurwar.trainingcourse.model.entity.User;

import java.sql.*;
import java.util.*;

public class JDBCActivityRequestDao implements ActivityRequestDao {
    private final Connection connection;
    private ResourceBundle resourceBundle = ResourceBundle.getBundle("database");

    public JDBCActivityRequestDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(ActivityRequest entity) {
        try (PreparedStatement ps = connection.prepareStatement("insert into activity_requests values (nextval('activity_requests_id_seq'), ?, ?, ?, ?, ?)")) {
            fillActivityRequestStatement(entity, ps);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<ActivityRequest> findById(long id) {
        try (PreparedStatement ps = connection.prepareStatement("select activity_requests.id as \"activity_requests.id\",\n" +
                "       activity_requests.activity_id as \"activity_requests.id\",\n" +
                "       activity_requests.user_id as \"activity_requests.user_id\",\n" +
                "       activity_requests.request_date as \"activity_requests.request_date\",\n" +
                "       activity_requests.action as \"activity_requests.action\",\n" +
                "       activity_requests.status as \"activity_requests.status\",\n" +
                "       activities.id as \"activities.id\",\n" +
                "       activities.name as \"activities.name\",\n" +
                "       activities.description as \"activities.description\",\n" +
                "       activities.start_time as \"activities.start_time\",\n" +
                "       activities.end_time as \"activities.end_time\",\n" +
                "       activities.duration as \"activities.duration\",\n" +
                "       activities.importance as \"activities.importance\",\n" +
                "       activities.status as \"activities.status\",\n" +
                "       users.id as \"users.id\",\n" +
                "       users.first_name as \"users.first_name\",\n" +
                "       users.last_name as \"users.last_name\",\n" +
                "       users.password as \"users.password\",\n" +
                "       users.username as \"users.username\",\n" +
                "       user_authorities.user_id as \"user_authorities.user_id\",\n" +
                "       user_authorities.authorities as \"user_authorities.authorities\"\n" +
                "from activity_requests\n" +
                "         left join activities on activity_requests.activity_id = activities.id\n" +
                "         left join users on activity_requests.user_id = users.id\n" +
                "         left join user_authorities on users.id = user_authorities.user_id " +
                "where users.id = ?")) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            Map<Long, ActivityRequest> activityRequestMap = extractMappedActivityRequests(rs);
            return activityRequestMap.values().stream().findAny();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ActivityRequest> findAll() {
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery("select activity_requests.id as \"activity_requests.id\",\n" +
                    "       activity_requests.activity_id as \"activity_requests.id\",\n" +
                    "       activity_requests.user_id as \"activity_requests.user_id\",\n" +
                    "       activity_requests.request_date as \"activity_requests.request_date\",\n" +
                    "       activity_requests.action as \"activity_requests.action\",\n" +
                    "       activity_requests.status as \"activity_requests.status\",\n" +
                    "       activities.id as \"activities.id\",\n" +
                    "       activities.name as \"activities.name\",\n" +
                    "       activities.description as \"activities.description\",\n" +
                    "       activities.start_time as \"activities.start_time\",\n" +
                    "       activities.end_time as \"activities.end_time\",\n" +
                    "       activities.duration as \"activities.duration\",\n" +
                    "       activities.importance as \"activities.importance\",\n" +
                    "       activities.status as \"activities.status\",\n" +
                    "       users.id as \"users.id\",\n" +
                    "       users.first_name as \"users.first_name\",\n" +
                    "       users.last_name as \"users.last_name\",\n" +
                    "       users.password as \"users.password\",\n" +
                    "       users.username as \"users.username\",\n" +
                    "       user_authorities.user_id as \"user_authorities.user_id\",\n" +
                    "       user_authorities.authorities as \"user_authorities.authorities\"\n" +
                    "from activity_requests\n" +
                    "         left join activities on activity_requests.activity_id = activities.id\n" +
                    "         left join users on activity_requests.user_id = users.id\n" +
                    "         left join user_authorities on users.id = user_authorities.user_id");

            Map<Long, ActivityRequest> activityRequestMap = extractMappedActivityRequests(rs);
            return new ArrayList<>(activityRequestMap.values());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(ActivityRequest entity) {
        try (PreparedStatement ps = connection.prepareStatement("update activity_requests set activity_id = ?, user_id = ?, request_date = ?, action = ?, status = ? where id = ?")) {
            fillActivityRequestStatement(entity, ps);
            ps.setLong(6, entity.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(long id) {
        try (PreparedStatement ps = connection.prepareStatement("delete from activity_requests where id = ?")) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }


    private void fillActivityRequestStatement(ActivityRequest entity, PreparedStatement ps) throws SQLException {
        ps.setLong(1, entity.getActivity().getId());
        ps.setLong(2, entity.getUser().getId());
        ps.setTimestamp(3, entity.getRequestDate() != null ? Timestamp.valueOf(entity.getRequestDate()) : null);
        ps.setString(4, entity.getAction().name());
        ps.setString(5, entity.getStatus().name());
    }

    private Map<Long, ActivityRequest> extractMappedActivityRequests(ResultSet rs) throws SQLException {
        Map<Long, ActivityRequest> activityRequestMap = new HashMap<>();
        Map<Long, User> userMap = new HashMap<>();
        Map<Long, Activity> activityMap = new HashMap<>();

        ActivityRequestMapper activityRequestMapper = new ActivityRequestMapper();
        UserMapper userMapper = new UserMapper();
        ActivityMapper activityMapper = new ActivityMapper();

        while (rs.next()) {
            ActivityRequest activityRequest = activityRequestMapper.extractFromResultSet(rs);
            Activity activity = activityMapper.extractFromResultSet(rs);
            User user = userMapper.extractFromResultSet(rs);
            Authority authority = Authority.valueOf(rs.getString("user_authorities.authorities"));

            user = userMapper.makeUnique(userMap, user);
            activity = activityMapper.makeUnique(activityMap, activity);
            user.getAuthorities().add(authority);
            activityRequest = activityRequestMapper.makeUnique(activityRequestMap, activityRequest);
            activityRequest.setUser(user);
            activityRequest.setActivity(activity);
        }
        return activityRequestMap;
    }
}
