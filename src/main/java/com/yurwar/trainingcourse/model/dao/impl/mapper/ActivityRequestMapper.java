package com.yurwar.trainingcourse.model.dao.impl.mapper;

import com.yurwar.trainingcourse.model.entity.Activity;
import com.yurwar.trainingcourse.model.entity.ActivityRequest;
import com.yurwar.trainingcourse.model.entity.ActivityRequestAction;
import com.yurwar.trainingcourse.model.entity.ActivityRequestStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Map;

public class ActivityRequestMapper implements ObjectMapper<ActivityRequest> {
    @Override
    public ActivityRequest extractFromResultSet(ResultSet rs) throws SQLException {
        ActivityRequest activityRequest = new ActivityRequest();
        activityRequest.setId(rs.getLong("activity_requests.id"));
        //TODO Check on add activity_id and user_id

        Timestamp dbTimestamp = rs.getTimestamp("activity_requests.request_date");
        activityRequest.setRequestDate(dbTimestamp != null ? dbTimestamp.toLocalDateTime() : null);

        activityRequest.setAction(ActivityRequestAction.valueOf(rs.getString("activity_requests.action")));
        activityRequest.setStatus(ActivityRequestStatus.valueOf(rs.getString("activity_requests.status")));

        return activityRequest;
    }

    @Override
    public ActivityRequest makeUnique(Map<Long, ActivityRequest> cache, ActivityRequest activityRequest) {
        cache.putIfAbsent(activityRequest.getId(), activityRequest);
        return cache.get(activityRequest.getId());
    }
}
