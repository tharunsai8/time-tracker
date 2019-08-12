package com.yurwar.trainingcourse.model.dao;

import com.yurwar.trainingcourse.model.entity.ActivityRequest;

import java.util.List;

/**
 * Implementation of generic dao to handle with activity request entity in database
 */
public interface ActivityRequestDao extends GenericDao<ActivityRequest> {
    /**
     * @param activityId id of activity in request
     * @param userId     id of user in request
     * @return list of activity requests with given id's
     */
    List<ActivityRequest> findByActivityIdAndUserId(long activityId, long userId);

    /**
     * Find pageable activity requests in database
     *
     * @param page page of activity requests list
     * @param size size of page of activity requests list
     * @return pageable list of activity requests
     */
    List<ActivityRequest> findAllPageable(int page, int size);

    /**
     * @return number of activity request records
     */
    long getNumberOfRecords();
}
