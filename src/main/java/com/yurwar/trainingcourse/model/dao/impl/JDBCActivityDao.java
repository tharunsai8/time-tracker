package com.yurwar.trainingcourse.model.dao.impl;

import com.yurwar.trainingcourse.model.dao.ActivityDao;
import com.yurwar.trainingcourse.model.dao.impl.mapper.ActivityMapper;
import com.yurwar.trainingcourse.model.dao.impl.mapper.UserMapper;
import com.yurwar.trainingcourse.model.entity.Activity;
import com.yurwar.trainingcourse.model.entity.Authority;
import com.yurwar.trainingcourse.model.entity.User;
import com.yurwar.trainingcourse.util.exception.DaoException;

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
            throw new DaoException("Can not create activity", e);
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
                "       activities.status            as \"activities.status\"\n" +
                "from activities\n" +
                "where activities.id = ?")) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();


            Map<Long, Activity> activityMap = extractActivitiesFromResultSet(rs);
            return activityMap.values().stream().findAny();
        } catch (SQLException e) {
            throw new DaoException("Can not find activity by id", e);
        }
    }

    @Override
    public List<Activity> findAll() {
        try (Statement ps = connection.createStatement()) {
            ResultSet rs = ps.executeQuery("select activities.id                as \"activities.id\",\n" +
                    "       activities.name              as \"activities.name\",\n" +
                    "       activities.description       as \"activities.description\",\n" +
                    "       activities.start_time        as \"activities.start_time\",\n" +
                    "       activities.end_time          as \"activities.end_time\",\n" +
                    "       activities.duration          as \"activities.duration\",\n" +
                    "       activities.importance        as \"activities.importance\",\n" +
                    "       activities.status            as \"activities.status\"\n" +
                    "from activities\n");

            Map<Long, Activity> activityMap = extractActivitiesFromResultSet(rs);
            return new ArrayList<>(activityMap.values());
        } catch (SQLException e) {
            throw new DaoException("Can not find all activities", e);
        }
    }

    @Override
    public List<Activity> findAllPageable(int page, int size) {
        try (PreparedStatement ps = connection.prepareStatement("select activities.id                as \"activities.id\",\n" +
                "       activities.name              as \"activities.name\",\n" +
                "       activities.description       as \"activities.description\",\n" +
                "       activities.start_time        as \"activities.start_time\",\n" +
                "       activities.end_time          as \"activities.end_time\",\n" +
                "       activities.duration          as \"activities.duration\",\n" +
                "       activities.importance        as \"activities.importance\",\n" +
                "       activities.status            as \"activities.status\"\n" +
                "from activities\n" +
                "order by activities.id desc " +
                "limit ? offset ?")) {
            ps.setInt(1, size);
            ps.setInt(2, size * page);
            ResultSet rs = ps.executeQuery();

            Map<Long, Activity> activityMap = extractActivitiesFromResultSet(rs);

            return new ArrayList<>(activityMap.values());
        } catch (SQLException e) {
            throw new DaoException("Can not find all activities", e);
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
            throw new DaoException("Can not update activity", e);
        }
    }

    @Override
    public void delete(long id) {
        try (PreparedStatement ps = connection.prepareStatement("delete from activities where id = ?")) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Can not delete activity", e);
        }
    }

    @Override
    public long getNumberOfRecords() {
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery("select count(*) from activities");

            if (rs.next()) {
                return rs.getLong(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new DaoException("Can not get number of records", e);
        }
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

    private Map<Long, Activity> extractActivitiesFromResultSet(ResultSet rs) throws SQLException {
        Map<Long, Activity> activityMap = new LinkedHashMap<>();
        Map<Long, User> userMap = new HashMap<>();

        UserMapper userMapper = new UserMapper();
        ActivityMapper activityMapper = new ActivityMapper();

        while (rs.next()) {
            Activity activity = activityMapper.extractFromResultSet(rs);
            activityMapper.makeUnique(activityMap, activity);
        }
        for (Activity activity : activityMap.values()) {
            try (PreparedStatement activityUsersPS = connection.prepareStatement("select " +
                    "       activities.id                as \"activities.id\",\n" +
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
                    "         left join user_authorities on users.id = user_authorities.user_id " +
                    "where activities.id = ?")) {
                activityUsersPS.setLong(1, activity.getId());
                ResultSet activityUsersRS = activityUsersPS.executeQuery();

                while (activityUsersRS.next()) {
                    User user = userMapper.extractFromResultSet(activityUsersRS);
                    user = userMapper.makeUnique(userMap, user);

                    if (activityUsersRS.getString("user_authorities.authorities") != null) {
                        Authority authority = Authority
                                .valueOf(activityUsersRS.getString("user_authorities.authorities"));
                        user.getAuthorities().add(authority);
                    }

                    if ((user.getId() != 0) && !activity.getUsers().contains(user)) {
                        activity.getUsers().add(user);
                    }
                }
            }
        }
        return activityMap;
    }
}
