package com.yurwar.trainingcourse.model.dao;

import com.yurwar.trainingcourse.model.entity.Activity;

import java.util.List;

public interface ActivityDao extends GenericDao<Activity> {
    List<Activity> findAllPageable(int page, int size);

    long getNumberOfRecords();
}
