package com.yurwar.trainingcourse.model.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.LocalDateTime;

public class ActivityRequest {
    private Long id;
    private Activity activity;
    private User user;
    private LocalDateTime requestDate;
    private ActivityRequestAction action;
    private ActivityRequestStatus status;

    public ActivityRequest() {
    }

    public ActivityRequest(Long id, Activity activity, User user, LocalDateTime requestDate, ActivityRequestAction action, ActivityRequestStatus status) {
        this.id = id;
        this.activity = activity;
        this.user = user;
        this.requestDate = requestDate;
        this.action = action;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }

    public ActivityRequestAction getAction() {
        return action;
    }

    public void setAction(ActivityRequestAction action) {
        this.action = action;
    }

    public ActivityRequestStatus getStatus() {
        return status;
    }

    public void setStatus(ActivityRequestStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ActivityRequest{" +
                "id=" + id +
                ", activity=" + activity +
                ", user=" + user +
                ", requestDate=" + requestDate +
                ", action=" + action +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof ActivityRequest)) return false;

        ActivityRequest that = (ActivityRequest) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(activity, that.activity)
                .append(user, that.user)
                .append(requestDate, that.requestDate)
                .append(action, that.action)
                .append(status, that.status)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(activity)
                .append(user)
                .append(requestDate)
                .append(action)
                .append(status)
                .toHashCode();
    }
}
