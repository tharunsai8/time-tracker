package com.yurwar.trainingcourse.model.dao.impl;

import com.yurwar.trainingcourse.model.dao.ActivityRequestDao;
import com.yurwar.trainingcourse.model.dao.impl.mapper.ActivityMapper;
import com.yurwar.trainingcourse.model.dao.impl.mapper.ActivityRequestMapper;
import com.yurwar.trainingcourse.model.dao.impl.mapper.UserMapper;
import com.yurwar.trainingcourse.model.entity.Activity;
import com.yurwar.trainingcourse.model.entity.ActivityRequest;
import com.yurwar.trainingcourse.model.entity.Authority;
import com.yurwar.trainingcourse.model.entity.User;
import com.yurwar.trainingcourse.util.exception.DaoException;

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
            throw new DaoException("Can not create activity request", e);
        }
    }

    @Override
    public List<ActivityRequest> findByActivityIdAndUserId(long activityId, long userId) {
        try (PreparedStatement ps = connection.prepareStatement("select activity_requests.id as \"activity_requests.id\",\n" +
                "       activity_requests.activity_id as \"activity_requests.id\",\n" +
                "       activity_requests.user_id as \"activity_requests.user_id\",\n" +
                "       activity_requests.request_date as \"activity_requests.request_date\",\n" +
                "       activity_requests.action as \"activity_requests.action\",\n" +
                "       activity_requests.status as \"activity_requests.status\"\n" +
                "from activity_requests " +
                "where activity_requests.activity_id = ? and activity_requests.user_id = ?")) {
            ps.setLong(1, activityId);
            ps.setLong(2, userId);
            ResultSet rs = ps.executeQuery();

            Map<Long, ActivityRequest> activityRequestMap = extractActivityRequestsFromResultSet(rs);
            return new ArrayList<>(activityRequestMap.values());
        } catch (SQLException e) {
            throw new DaoException("Can not find activity request", e);
        }
    }

    @Override
    public Optional<ActivityRequest> findById(long id) {
        try (PreparedStatement ps = connection.prepareStatement("select activity_requests.id as \"activity_requests.id\",\n" +
                "       activity_requests.activity_id as \"activity_requests.id\",\n" +
                "       activity_requests.user_id as \"activity_requests.user_id\",\n" +
                "       activity_requests.request_date as \"activity_requests.request_date\",\n" +
                "       activity_requests.action as \"activity_requests.action\",\n" +
                "       activity_requests.status as \"activity_requests.status\"\n" +
                "from activity_requests " +
                "where activity_requests.id = ?")) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            Map<Long, ActivityRequest> activityRequestMap = extractActivityRequestsFromResultSet(rs);
            return activityRequestMap.values().stream().findAny();
        } catch (SQLException e) {
            throw new DaoException("Can not find activity request", e);
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
                    "       activity_requests.status as \"activity_requests.status\"\n" +
                    "from activity_requests ");

            Map<Long, ActivityRequest> activityRequestMap = extractActivityRequestsFromResultSet(rs);
            return new ArrayList<>(activityRequestMap.values());
        } catch (SQLException e) {
            throw new DaoException("Can not find all activity requests", e);
        }
    }

    @Override
    public List<ActivityRequest> findAllPageable(int page, int size) {
        try (PreparedStatement ps = connection.prepareStatement("select activity_requests.id as \"activity_requests.id\",\n" +
                "       activity_requests.activity_id as \"activity_requests.id\",\n" +
                "       activity_requests.user_id as \"activity_requests.user_id\",\n" +
                "       activity_requests.request_date as \"activity_requests.request_date\",\n" +
                "       activity_requests.action as \"activity_requests.action\",\n" +
                "       activity_requests.status as \"activity_requests.status\"\n" +
                "from activity_requests " +
                "order by activity_requests.id desc " +
                "limit ? offset ?")) {
            ps.setLong(1, size);
            ps.setLong(2, size * page);
            ResultSet rs = ps.executeQuery();

            Map<Long, ActivityRequest> activityRequestMap = extractActivityRequestsFromResultSet(rs);
            return new ArrayList<>(activityRequestMap.values());
        } catch (SQLException e) {
            throw new DaoException("Can not find all activity requests", e);
        }
    }

    @Override
    public void update(ActivityRequest entity) {
        try (PreparedStatement ps = connection.prepareStatement("update activity_requests set activity_id = ?, user_id = ?, request_date = ?, action = ?, status = ? where id = ?")) {
            fillActivityRequestStatement(entity, ps);
            ps.setLong(6, entity.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Can not update activity request", e);
        }
    }

    @Override
    public void delete(long id) {
        try (PreparedStatement ps = connection.prepareStatement("delete from activity_requests where id = ?")) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Can not delete activity", e);
        }
    }

    @Override
    public long getNumberOfRecords() {
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery("select count(*) from activity_requests");

            if (rs.next()) {
                return rs.getLong(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new DaoException("Can not get number of records", e);
        }
    }

    private void fillActivityRequestStatement(ActivityRequest entity, PreparedStatement ps) throws SQLException {
        ps.setLong(1, entity.getActivity().getId());
        ps.setLong(2, entity.getUser().getId());
        ps.setTimestamp(3, entity.getRequestDate() != null ? Timestamp.valueOf(entity.getRequestDate()) : null);
        ps.setString(4, entity.getAction().name());
        ps.setString(5, entity.getStatus().name());
    }

    private Map<Long, ActivityRequest> extractActivityRequestsFromResultSet(ResultSet rs) throws SQLException {
        Map<Long, ActivityRequest> activityRequestMap = new LinkedHashMap<>();
        Map<Long, User> userMap = new HashMap<>();
        Map<Long, Activity> activityMap = new HashMap<>();

        ActivityRequestMapper activityRequestMapper = new ActivityRequestMapper();
        UserMapper userMapper = new UserMapper();
        ActivityMapper activityMapper = new ActivityMapper();

        while (rs.next()) {
            ActivityRequest activityRequest = activityRequestMapper.extractFromResultSet(rs);
            activityRequestMapper.makeUnique(activityRequestMap, activityRequest);
        }

        for (ActivityRequest activityRequest : activityRequestMap.values()) {
            try (PreparedStatement activitiesPS = connection.prepareStatement("select " +
                    "       activity_requests.id as \"activity_requests.id\",\n" +
                    "       activities.id as \"activities.id\",\n" +
                    "       activities.name as \"activities.name\",\n" +
                    "       activities.description as \"activities.description\",\n" +
                    "       activities.start_time as \"activities.start_time\",\n" +
                    "       activities.end_time as \"activities.end_time\",\n" +
                    "       activities.duration as \"activities.duration\",\n" +
                    "       activities.importance as \"activities.importance\",\n" +
                    "       activities.status as \"activities.status\"\n" +
                    "from activity_requests\n" +
                    "         left join activities on activity_requests.activity_id = activities.id" +
                    " where activity_requests.id = ?")) {
                activitiesPS.setLong(1, activityRequest.getId());
                ResultSet activitiesResultSet = activitiesPS.executeQuery();

                while (activitiesResultSet.next()) {
                    Activity activity = activityMapper.extractFromResultSet(activitiesResultSet);
                    activity = activityMapper.makeUnique(activityMap, activity);

                    if (activity.getId() != 0) {
                        activityRequest.setActivity(activity);
                    }
                }
            }

            try (PreparedStatement usersPS = connection.prepareStatement("select" +
                    "       activity_requests.id as \"activity_requests.id\",\n" +
                    "       users.id as \"users.id\",\n" +
                    "       users.first_name as \"users.first_name\",\n" +
                    "       users.last_name as \"users.last_name\",\n" +
                    "       users.password as \"users.password\",\n" +
                    "       users.username as \"users.username\",\n" +
                    "       user_authorities.user_id as \"user_authorities.user_id\",\n" +
                    "       user_authorities.authorities as \"user_authorities.authorities\"\n" +
                    "from activity_requests\n" +
                    "         left join users on activity_requests.user_id = users.id\n" +
                    "         left join user_authorities on users.id = user_authorities.user_id " +
                    "where activity_requests.id = ?")) {
                usersPS.setLong(1, activityRequest.getId());
                ResultSet usersResultSet = usersPS.executeQuery();

                while (usersResultSet.next()) {
                    User user = userMapper.extractFromResultSet(usersResultSet);
                    user = userMapper.makeUnique(userMap, user);

                    if (usersResultSet.getString("user_authorities.authorities") != null) {
                        Authority authority = Authority
                                .valueOf(usersResultSet.getString("user_authorities.authorities"));
                        user.getAuthorities().add(authority);
                    }

                    if (user.getId() != 0) {
                        activityRequest.setUser(user);
                    }
                }
            }
        }
        return activityRequestMap;
    }
}
