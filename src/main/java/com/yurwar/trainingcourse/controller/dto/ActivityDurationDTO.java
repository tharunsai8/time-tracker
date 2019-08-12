package com.yurwar.trainingcourse.controller.dto;

/**
 * Data transfer object to transport activity duration data from command to service
 *
 * @author Yurii Matora
 * @see com.yurwar.trainingcourse.model.entity.Activity
 * @see com.yurwar.trainingcourse.controller.command.Command
 */
public class ActivityDurationDTO {
    private int days;
    private int hours;
    private int minutes;

    public ActivityDurationDTO(int days, int hours, int minutes) {
        this.days = days;
        this.hours = hours;
        this.minutes = minutes;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }
}
