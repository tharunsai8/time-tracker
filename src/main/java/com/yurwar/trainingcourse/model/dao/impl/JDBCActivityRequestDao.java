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
    private final ResourceBundle rb = ResourceBundle.getBundle("database");

    JDBCActivityRequestDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(ActivityRequest entity) {
        try (PreparedStatement ps = connection.prepareStatement(rb.getString("query.activity_request.create"))) {
            fillActivityRequestStatement(entity, ps);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Can not create activity request", e);
        }
    }

    @Override
    public List<ActivityRequest> findByActivityIdAndUserId(long activityId, long userId) {
        try (PreparedStatement ps = connection.prepareStatement(rb.getString("query.activity_request.find.by_activity_id_and_user_id"))) {
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
        try (PreparedStatement ps = connection.prepareStatement(rb.getString("query.activity_request.find.by_id"))) {
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
            ResultSet rs = st.executeQuery(rb.getString("query.activity_request.find.all"));

            Map<Long, ActivityRequest> activityRequestMap = extractActivityRequestsFromResultSet(rs);
            return new ArrayList<>(activityRequestMap.values());
        } catch (SQLException e) {
            throw new DaoException("Can not find all activity requests", e);
        }
    }

    @Override
    public List<ActivityRequest> findAllPageable(int page, int size) {
        try (PreparedStatement ps = connection.prepareStatement(rb.getString("query.activity_request.find.all.pageable"))) {
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
        try (PreparedStatement ps = connection.prepareStatement(rb.getString("query.activity_request.update"))) {
            fillActivityRequestStatement(entity, ps);
            ps.setLong(6, entity.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Can not update activity request", e);
        }
    }

    @Override
    public void delete(long id) {
        try (PreparedStatement ps = connection.prepareStatement(rb.getString("query.activity_request.delete"))) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Can not delete activity", e);
        }
    }

    @Override
    public long getNumberOfRecords() {
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(rb.getString("query.activity_request.find.rows"));

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
            try (PreparedStatement activitiesPS = connection.prepareStatement(rb.getString("query.activity_request.join.activity"))) {
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

            try (PreparedStatement usersPS = connection.prepareStatement(rb.getString("query.activity_request.join.user"))) {
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
