package com.yurwar.trainingcourse.model.service;

import com.yurwar.trainingcourse.model.dao.*;
import com.yurwar.trainingcourse.model.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class ActivityRequestService {
    private static final Logger log = LogManager.getLogger();
    private final DaoFactory daoFactory = DaoFactory.getInstance();

    public List<ActivityRequest> getAllActivityRequestsPageable(int page, int size) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            ActivityRequestDao activityRequestDao = daoFactory.createActivityRequestDao(connection);
            return activityRequestDao.findAllPageable(page, size);
        } catch (Exception e) {
            log.warn("Can not get all activity requests", e);
        }
        return Collections.emptyList();
    }

    public void makeAddActivityRequest(long userId, long activityId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.createUserDao(connection);
            ActivityDao activityDao = daoFactory.createActivityDao(connection);
            ActivityRequestDao activityRequestDao = daoFactory.createActivityRequestDao(connection);

            connection.beginTransaction();

            User user = userDao.findById(userId).orElseThrow(() ->
                    new IllegalArgumentException("Invalid user id: " + userId));
            Activity activity = activityDao.findById(activityId).orElseThrow(() ->
                    new IllegalArgumentException("Invalid activity id: " + activityId));

            long currentActivityRequestsCount = activityRequestDao
                    .findByActivityIdAndUserId(activityId, userId)
                    .stream()
                    .filter(activityRequest ->
                            activityRequest.getAction().equals(ActivityRequestAction.ADD) &&
                                    activityRequest.getStatus().equals(ActivityRequestStatus.PENDING))
                    .count();
            if (currentActivityRequestsCount > 0) {
                log.info("User already send activity request");
                return;
            }

            switch (activity.getStatus()) {
                case COMPLETED:
                    log.info("Activity already completed");
                    break;
                case ACTIVE: {
                    if (activity.getUsers().contains(user)) {
                        log.info("User already in activity");
                        break;
                    }
                    //TODO Try to remove break
                    ActivityRequest activityRequest = new ActivityRequest();
                    activityRequest.setUser(user);
                    activityRequest.setActivity(activity);
                    activityRequest.setAction(ActivityRequestAction.ADD);
                    activityRequest.setStatus(ActivityRequestStatus.PENDING);
                    activityRequest.setRequestDate(LocalDateTime.now());

                    activityRequestDao.create(activityRequest);
                    break;
                }
                case PENDING: {
                    ActivityRequest activityRequest = new ActivityRequest();
                    activityRequest.setUser(user);
                    activityRequest.setActivity(activity);
                    activityRequest.setAction(ActivityRequestAction.ADD);
                    activityRequest.setStatus(ActivityRequestStatus.PENDING);
                    activityRequest.setRequestDate(LocalDateTime.now());

                    activityRequestDao.create(activityRequest);
                    break;
                }
            }

            connection.commit();
        } catch (Exception e) {
            log.warn("Can not make add activity request", e);
        }
    }

    public void makeCompleteActivityRequest(long userId, long activityId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.createUserDao(connection);
            ActivityDao activityDao = daoFactory.createActivityDao(connection);
            ActivityRequestDao activityRequestDao = daoFactory.createActivityRequestDao(connection);

            connection.beginTransaction();

            User user = userDao.findById(userId).orElseThrow(() ->
                    new IllegalArgumentException("Invalid user id: " + userId));
            Activity activity = activityDao.findById(activityId).orElseThrow(() ->
                    new IllegalArgumentException("Invalid activity id: " + activityId));

            long currentActivityRequestsCount = activityRequestDao
                    .findByActivityIdAndUserId(activityId, userId)
                    .stream()
                    .filter(activityRequest ->
                            activityRequest.getAction().equals(ActivityRequestAction.COMPLETE) &&
                                    activityRequest.getStatus().equals(ActivityRequestStatus.PENDING))
                    .count();
            if (currentActivityRequestsCount > 0) {
                log.info("User {} already sent activity request", user.getUsername());
                return;
            }

            switch (activity.getStatus()) {
                case COMPLETED:
                    log.info("Activity {} already completed", activity.getName());
                    break;
                case ACTIVE:
                    if (activity.getUsers().contains(user)) {
                        ActivityRequest activityRequest = new ActivityRequest();
                        activityRequest.setUser(user);
                        activityRequest.setActivity(activity);
                        activityRequest.setAction(ActivityRequestAction.COMPLETE);
                        activityRequest.setStatus(ActivityRequestStatus.PENDING);
                        activityRequest.setRequestDate(LocalDateTime.now());

                        activityRequestDao.create(activityRequest);
                        return;
                    }
                    break;
                case PENDING:
                    log.info("User {} can not complete pending activity", user.getUsername());
                    break;
            }

            connection.commit();
        } catch (Exception e) {
            log.warn("Can not make complete activity request", e);
        }
    }

    public ActivityRequest findActivityRequestById(long activityRequestId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            ActivityRequestDao activityRequestDao = daoFactory.createActivityRequestDao(connection);

            return activityRequestDao.findById(activityRequestId).orElseThrow(() ->
                    new IllegalArgumentException("Invalid activity request id: " + activityRequestId));
        } catch (Exception e) {
            log.warn("Can not find activity request");
        }
        return null;
    }

    public void approveAddActivityRequest(long activityRequestId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            ActivityDao activityDao = daoFactory.createActivityDao(connection);
            ActivityRequestDao activityRequestDao = daoFactory.createActivityRequestDao(connection);

            connection.beginTransaction();

            ActivityRequest activityRequest = findActivityRequestById(activityRequestId);
            Activity activity = activityRequest.getActivity();
            User user = activityRequest.getUser();

            switch (activity.getStatus()) {
                case PENDING: {
                    activity.setStartTime(LocalDateTime.now());
                    activity.setStatus(ActivityStatus.ACTIVE);
                    activity.getUsers().add(user);
                    user.getActivities().add(activity);
                    activityRequest.setStatus(ActivityRequestStatus.APPROVED);

                    activityDao.update(activity);
                    break;
                }
                case ACTIVE: {
                    activity.getUsers().add(user);
                    user.getActivities().add(activity);
                    activityRequest.setStatus(ActivityRequestStatus.APPROVED);

                    activityDao.update(activity);
                    break;
                }
                case COMPLETED: {
                    log.info("Can not approve add request for completed activity");
                    activityRequest.setStatus(ActivityRequestStatus.REJECTED);
                    break;
                }
            }
            activityRequestDao.update(activityRequest);

            connection.commit();
        } catch (Exception e) {
            log.warn("Can not approve add activity request", e);
        }
    }

    public void approveCompleteActivityRequest(long activityRequestId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            ActivityDao activityDao = daoFactory.createActivityDao(connection);
            ActivityRequestDao activityRequestDao = daoFactory.createActivityRequestDao(connection);

            connection.beginTransaction();

            ActivityRequest activityRequest = findActivityRequestById(activityRequestId);
            Activity activity = activityRequest.getActivity();

            switch (activity.getStatus()) {
                case PENDING: {
                    log.info("Can not approve complete request for pending activity");
                    break;
                }
                case ACTIVE: {
                    activity.setEndTime(LocalDateTime.now());
                    activity.setStatus(ActivityStatus.COMPLETED);
                    activityRequest.setStatus(ActivityRequestStatus.APPROVED);
                    activityDao.update(activity);
                    activityRequestDao.update(activityRequest);
                    break;
                }
                case COMPLETED: {
                    log.info("Can not approve complete request for completed activity");
                    activityRequest.setStatus(ActivityRequestStatus.REJECTED);
                    activityRequestDao.update(activityRequest);
                    break;
                }
            }

            connection.commit();
        } catch (Exception e) {
            log.warn("Can not approve complete activity request", e);
        }
    }

    public void rejectActivityRequest(long activityRequestId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            ActivityRequestDao activityRequestDao = daoFactory.createActivityRequestDao(connection);

            connection.beginTransaction();

            ActivityRequest activityRequest = findActivityRequestById(activityRequestId);
            activityRequest.setStatus(ActivityRequestStatus.REJECTED);
            activityRequestDao.update(activityRequest);

            connection.commit();
        } catch (Exception e) {
            log.warn("Can not reject activity request", e);
        }
    }

    public long getNumberOfRecords() {
        try (DaoConnection connection = daoFactory.getConnection()) {
            ActivityRequestDao activityRequestDao = daoFactory.createActivityRequestDao(connection);

            return activityRequestDao.getNumbersOfRecords();
        } catch (Exception e) {
            log.warn("Can not get number of activity requests", e);
        }
        return 0;
    }
}
