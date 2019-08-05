package com.yurwar.trainingcourse.model.dao.impl.mapper;

import com.yurwar.trainingcourse.model.entity.Activity;
import com.yurwar.trainingcourse.model.entity.ActivityImportance;
import com.yurwar.trainingcourse.model.entity.ActivityStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.Map;

public class ActivityMapper implements ObjectMapper<Activity> {
    @Override
    public Activity extractFromResultSet(ResultSet rs) throws SQLException {
        Activity activity = new Activity();
        activity.setId(rs.getLong(10));
        activity.setName(rs.getString(11));
        activity.setDescription(rs.getString(12));

        Timestamp dbTimestamp = rs.getTimestamp(13);
        activity.setStartTime(dbTimestamp != null ? dbTimestamp.toLocalDateTime() : null);

        dbTimestamp = rs.getTimestamp(14);
        activity.setEndTime(dbTimestamp != null ? dbTimestamp.toLocalDateTime() : null);

        activity.setDuration(Duration.ofSeconds(rs.getLong(15)));
        activity.setImportance(ActivityImportance.valueOf(rs.getString(16)));
        activity.setStatus(ActivityStatus.valueOf(rs.getString(17)));

        return activity;
    }

    @Override
    public Activity makeUnique(Map<Long, Activity> cache, Activity activity) {
        cache.putIfAbsent(activity.getId(), activity);
        return cache.get(activity.getId());
    }
}
