package com.yurwar.trainingcourse.model.dao;

import com.yurwar.trainingcourse.model.entity.ActivityRequest;

import java.util.Arrays;
import java.util.List;

public interface ActivityRequestDao extends GenericDao<ActivityRequest> {
    List<ActivityRequest> findByActivityIdAndUserId(long activityId, long userId);
}
