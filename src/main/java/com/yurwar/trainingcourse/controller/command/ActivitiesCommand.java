package com.yurwar.trainingcourse.controller.command;

import com.yurwar.trainingcourse.model.entity.Activity;
import com.yurwar.trainingcourse.model.service.ActivityService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ActivitiesCommand implements Command {
    private final ActivityService activityService;

    public ActivitiesCommand(ActivityService activityService) {
        this.activityService = activityService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        List<Activity> activities = activityService.getAllActivities();
        return "activities";
    }
}
