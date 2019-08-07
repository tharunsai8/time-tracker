package com.yurwar.trainingcourse.model.dao.impl;

import com.yurwar.trainingcourse.model.dao.ActivityDao;
import com.yurwar.trainingcourse.model.dao.impl.mapper.ActivityMapper;
import com.yurwar.trainingcourse.model.dao.impl.mapper.ActivityRequestMapper;
import com.yurwar.trainingcourse.model.dao.impl.mapper.UserMapper;
import com.yurwar.trainingcourse.model.entity.Activity;
import com.yurwar.trainingcourse.model.entity.ActivityRequest;
import com.yurwar.trainingcourse.model.entity.Authority;
import com.yurwar.trainingcourse.model.entity.User;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class JDBCActivityDao implements ActivityDao {
    private final Connection connection;
    private ResourceBundle resourceBundle = ResourceBundle.getBundle("database");

    JDBCActivityDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Activity entity) {
        try (PreparedStatement ps = connection.prepareStatement("insert into activities values (nextval('activities_id_seq'), ?, ?, ?, ?, ?, ?, ?)")) {
            fillStatement(entity, ps);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Activity> findById(long id) {
        try (PreparedStatement ps = connection.prepareStatement("select activities.id                as \"activities.id\",\n" +
                "       activities.name              as \"activities.name\",\n" +
                "       activities.description       as \"activities.description\",\n" +
                "       activities.start_time        as \"activities.start_time\",\n" +
                "       activities.end_time          as \"activities.end_time\",\n" +
                "       activities.duration          as \"activities.duration\",\n" +
                "       activities.importance        as \"activities.importance\",\n" +
                "       activities.status            as \"activities.status\",\n" +
                "       users_activities.user_id     as \"users_activities.user_id\",\n" +
                "       users_activities.activity_id as \"users_activities.activity_id\",\n" +
                "       users.id                     as \"users.id\",\n" +
                "       users.first_name             as \"users.first_name\",\n" +
                "       users.last_name              as \"users.last_name\",\n" +
                "       users.password               as \"users.password\",\n" +
                "       users.username               as \"users.username\",\n" +
                "       user_authorities.user_id as \"user_authorities.user_id\",\n" +
                "       user_authorities.authorities as \"user_authorities.authorities\"\n" +
                "from activities\n" +
                "         left join users_activities on activities.id = users_activities.activity_id\n" +
                "         left join users on users_activities.user_id = users.id " +
                "         left join user_authorities on users.id = user_authorities.user_id" +
                " where activities.id = ?")) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            Map<Long, Activity> activityMap = extractMappedActivities(rs);
            return activityMap.values().stream().findAny();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Activity> findAll() {
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery("select activities.id                as \"activities.id\",\n" +
                    "       activities.name              as \"activities.name\",\n" +
                    "       activities.description       as \"activities.description\",\n" +
                    "       activities.start_time        as \"activities.start_time\",\n" +
                    "       activities.end_time          as \"activities.end_time\",\n" +
                    "       activities.duration          as \"activities.duration\",\n" +
                    "       activities.importance        as \"activities.importance\",\n" +
                    "       activities.status            as \"activities.status\",\n" +
                    "       users_activities.user_id     as \"users_activities.user_id\",\n" +
                    "       users_activities.activity_id as \"users_activities.activity_id\",\n" +
                    "       users.id                     as \"users.id\",\n" +
                    "       users.first_name             as \"users.first_name\",\n" +
                    "       users.last_name              as \"users.last_name\",\n" +
                    "       users.password               as \"users.password\",\n" +
                    "       users.username               as \"users.username\",\n" +
                    "       user_authorities.user_id as \"user_authorities.user_id\",\n" +
                    "       user_authorities.authorities as \"user_authorities.authorities\"\n" +
                    "from activities\n" +
                    "         left join users_activities on activities.id = users_activities.activity_id\n" +
                    "         left join users on users_activities.user_id = users.id " +
                    "         left join user_authorities on users.id = user_authorities.user_id");

            Map<Long, Activity> activityMap = extractMappedActivities(rs);
            return new ArrayList<>(activityMap.values());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Activity entity) {
        try (PreparedStatement activityPS = connection.prepareStatement("update activities set name = ?, description = ?, start_time =  ?, end_time = ?, duration = ?, importance = ?, status = ? where id = ?")) {
            fillStatement(entity, activityPS);
            activityPS.setLong(8, entity.getId());
            activityPS.executeUpdate();
            try (PreparedStatement usersActivitiesPS = connection.prepareStatement("insert into users_activities values (?, ?) on conflict do nothing")) {
                List<Long> userIdList = entity.getUsers().stream().map(User::getId).collect(Collectors.toList());
                for (long userId : userIdList) {
                    usersActivitiesPS.setLong(1, userId);
                    usersActivitiesPS.setLong(2, entity.getId());
                    usersActivitiesPS.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(long id) {
        try (PreparedStatement ps = connection.prepareStatement("delete from activities where id = ?")) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }

    private void fillStatement(Activity entity, PreparedStatement ps) throws SQLException {
        ps.setString(1, entity.getName());
        ps.setString(2, entity.getDescription());
        ps.setTimestamp(3, entity.getStartTime() != null ? Timestamp.valueOf(entity.getStartTime()) : null);
        ps.setTimestamp(4, entity.getEndTime() != null ? Timestamp.valueOf(entity.getEndTime()) : null);
        ps.setLong(5, entity.getDuration() != null ? entity.getDuration().toSeconds() : 0);
        ps.setString(6, entity.getImportance().name());
        ps.setString(7, entity.getStatus().name());
    }

    private Map<Long, Activity> extractMappedActivities(ResultSet rs) throws SQLException {
        Map<Long, Activity> activityMap = new HashMap<>();
        Map<Long, User> userMap = new HashMap<>();

        UserMapper userMapper = new UserMapper();
        ActivityMapper activityMapper = new ActivityMapper();

        while (rs.next()) {
            Activity activity = activityMapper.extractFromResultSet(rs);
            User user = userMapper.extractFromResultSet(rs);
            String authorityStr = rs.getString("user_authorities.authorities");

            user = userMapper.makeUnique(userMap, user);
            activity = activityMapper.makeUnique(activityMap, activity);
            if (authorityStr != null) {
                user.getAuthorities().add(Authority.valueOf(authorityStr));
            }

            if (!activity.getUsers().contains(user) && user.getId() != 0) {
                activity.getUsers().add(user);
            }
        }
        return activityMap;
    }
}
