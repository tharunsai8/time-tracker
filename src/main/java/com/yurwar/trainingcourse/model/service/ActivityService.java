package com.yurwar.trainingcourse.model.service;

import com.yurwar.trainingcourse.model.dao.ActivityDao;
import com.yurwar.trainingcourse.model.dao.DaoConnection;
import com.yurwar.trainingcourse.model.dao.DaoFactory;
import com.yurwar.trainingcourse.model.entity.Activity;
import com.yurwar.trainingcourse.model.entity.ActivityStatus;
import com.yurwar.trainingcourse.model.entity.User;
import com.yurwar.trainingcourse.util.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ActivityService {
    private final DaoFactory daoFactory = DaoFactory.getInstance();
    private static final Logger log = LogManager.getLogger();

    public List<Activity> getAllActivitiesPageable(int page, int size) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            ActivityDao activityDao = daoFactory.createActivityDao(connection);
            return activityDao.findAllPageable(page, size);
        } catch (DaoException e) {
            log.warn("Can not get all users", e);
        }
        return Collections.emptyList();
    }

    public void createActivity(Activity activity) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            ActivityDao activityDao = daoFactory.createActivityDao(connection);
            activityDao.create(activity);
        } catch (DaoException e) {
            log.warn("Can not create activity", e);
        }
    }

    public void deleteActivity(long activityId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            ActivityDao activityDao = daoFactory.createActivityDao(connection);
            activityDao.delete(activityId);
        } catch (DaoException e) {
            log.warn("Can not delete activity", e);
        }
    }

    public Optional<Activity> getActivityById(long activityId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            ActivityDao activityDao = daoFactory.createActivityDao(connection);
            return activityDao.findById(activityId);
        } catch (DaoException e) {
            log.warn("Can not get activity by id", e);
        }
        return Optional.empty();
    }

    public long getNumberOfRecords() {
        try (DaoConnection connection = daoFactory.getConnection()) {
            ActivityDao activityDao = daoFactory.createActivityDao(connection);
            return activityDao.getNumberOfRecords();
        } catch (DaoException e) {
            log.warn("Can not get number of activities", e);
        }
        return 0;
    }

    public void markTimeSpent(long activityId, User user, int days, int hours, int minutes) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            ActivityDao activityDao = daoFactory.createActivityDao(connection);

            connection.beginTransaction();

            Activity activity = getActivityById(activityId).orElseThrow(() ->
                    new IllegalArgumentException("Invalid activity id: " + activityId));

            if (activity.getStatus().equals(ActivityStatus.ACTIVE) && activity.getUsers().contains(user)) {
                Duration duration = activity.getDuration();
                duration = duration
                        .plusDays(days)
                        .plusHours(hours)
                        .plusMinutes(minutes);

                activity.setDuration(duration);
                activityDao.update(activity);
            }

            connection.commit();
        }
    }
}
