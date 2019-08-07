package com.yurwar.trainingcourse.controller.command;

import com.yurwar.trainingcourse.model.service.ActivityRequestService;

import javax.servlet.http.HttpServletRequest;

public class ActivityRequestsCommand implements Command {
    private final ActivityRequestService activityRequestService;

    public ActivityRequestsCommand(ActivityRequestService activityRequestService) {
        this.activityRequestService = activityRequestService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        request.setAttribute("activityRequests", activityRequestService.getAllActivityRequests());
        return "/activity-requests.jsp";
    }
}
