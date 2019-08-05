package com.yurwar.trainingcourse.model.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class User {
    private long id;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private Set<Authority> authorities = new HashSet<>();
    private List<Activity> activities = new ArrayList<>();
    private List<ActivityRequest> activityRequests = new ArrayList<>();

    public User() {}

    public User(long id,
                String username,
                String firstName,
                String lastName,
                String password,
                Set<Authority> authorities,
                List<Activity> activities, List<ActivityRequest> activityRequests) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.authorities = authorities;
        this.activities = activities;
        this.activityRequests = activityRequests;
    }

    public User(String username,
                String firstName,
                String lastName,
                String password,
                Set<Authority> authorities,
                List<Activity> activities,
                List<ActivityRequest> activityRequests) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.authorities = authorities;
        this.activities = activities;
        this.activityRequests = activityRequests;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof User)) return false;

        User user = (User) o;

        return new EqualsBuilder()
                .append(id, user.id)
                .append(username, user.username)
                .append(firstName, user.firstName)
                .append(lastName, user.lastName)
                .append(password, user.password)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(username)
                .append(firstName)
                .append(lastName)
                .append(password)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", authorities=" + authorities +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    public List<ActivityRequest> getActivityRequests() {
        return activityRequests;
    }

    public void setActivityRequests(List<ActivityRequest> activityRequests) {
        this.activityRequests = activityRequests;
    }
}
