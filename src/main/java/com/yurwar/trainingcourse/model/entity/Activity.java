package com.yurwar.trainingcourse.model.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Activity {
    private long id;
    private String name;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Duration duration;
    private ActivityImportance importance;
    private ActivityStatus status;
    private List<User> users = new ArrayList<>();
    private List<ActivityRequest> activityRequests = new ArrayList<>();

    public Activity() {
    }

    public Activity(long id, String name, String description, LocalDateTime startTime, LocalDateTime endTime, Duration duration, ActivityImportance importance, ActivityStatus status, List<User> users, List<ActivityRequest> activityRequests) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.importance = importance;
        this.status = status;
        this.users = users;
        this.activityRequests = activityRequests;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Activity)) return false;

        Activity activity = (Activity) o;

        return new EqualsBuilder()
                .append(id, activity.id)
                .append(name, activity.name)
                .append(description, activity.description)
                .append(startTime, activity.startTime)
                .append(endTime, activity.endTime)
                .append(duration, activity.duration)
                .append(importance, activity.importance)
                .append(status, activity.status)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(description)
                .append(startTime)
                .append(endTime)
                .append(duration)
                .append(importance)
                .append(status)
                .toHashCode();
    }


    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", duration=" + duration +
                ", importance=" + importance +
                ", status=" + status +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public ActivityImportance getImportance() {
        return importance;
    }

    public void setImportance(ActivityImportance importance) {
        this.importance = importance;
    }

    public ActivityStatus getStatus() {
        return status;
    }

    public void setStatus(ActivityStatus status) {
        this.status = status;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<ActivityRequest> getActivityRequests() {
        return activityRequests;
    }

    public void setActivityRequests(List<ActivityRequest> activityRequests) {
        this.activityRequests = activityRequests;
    }
}
