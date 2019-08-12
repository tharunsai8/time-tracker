package com.yurwar.trainingcourse.model.dao;

import com.yurwar.trainingcourse.model.entity.Activity;

import java.util.List;

/**
 * Implementation of generic dao to handle with activity entity in database
 */
public interface ActivityDao extends GenericDao<Activity> {
    /**
     * Find pageable activities in database
     *
     * @param page page of activities list
     * @param size size of page of activities list
     * @return pageable list of activities
     */
    List<Activity> findAllPageable(int page, int size);

    /**
     * @return number of activity records
     */
    long getNumberOfRecords();
}
