package com.yurwar.trainingcourse.model.service;

import com.yurwar.trainingcourse.model.dao.ActivityRequestDao;
import com.yurwar.trainingcourse.model.dao.DaoFactory;
import com.yurwar.trainingcourse.model.entity.ActivityRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;

public class ActivityRequestService {
    private final DaoFactory daoFactory = DaoFactory.getInstance();
    private static final Logger log = LogManager.getLogger();

    public List<ActivityRequest> getAllActivityRequests() {
        try (ActivityRequestDao activityRequestDao = daoFactory.createActivityRequestDao()) {
            activityRequestDao.findAll().stream().map(ActivityRequest::getUser).forEach(System.out::println);
            return activityRequestDao.findAll();
        } catch (Exception e) {
            log.warn("Can not get all activity requests", e);
        }
        return Collections.emptyList();
    }
}
