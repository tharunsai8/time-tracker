package com.yurwar.trainingcourse.controller.command;

import com.yurwar.trainingcourse.model.service.ActivityService;

import javax.servlet.http.HttpServletRequest;

public class ActivityDeleteCommand implements Command {
    private ActivityService activityService;

    public ActivityDeleteCommand(ActivityService activityService) {
        this.activityService = activityService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        long activityId = Long.parseLong(request.getParameter("id"));
        activityService.deleteActivity(activityId);
        return "redirect:/activities";
    }
}
