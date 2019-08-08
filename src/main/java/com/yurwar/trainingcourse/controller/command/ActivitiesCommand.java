package com.yurwar.trainingcourse.controller.command;

import com.yurwar.trainingcourse.model.service.ActivityService;

import javax.servlet.http.HttpServletRequest;

public class ActivitiesCommand implements Command {
    private final ActivityService activityService;

    public ActivitiesCommand(ActivityService activityService) {
        this.activityService = activityService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        int page = 0;
        int size = 5;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        if (request.getParameter("size") != null) {
            size = Integer.parseInt(request.getParameter("size"));
        }

        request.setAttribute("activities", activityService.getAllActivitiesPageable(page, size));
        request.setAttribute("page", page);
        request.setAttribute("size", size);
        return "/activities.jsp";
    }
}
