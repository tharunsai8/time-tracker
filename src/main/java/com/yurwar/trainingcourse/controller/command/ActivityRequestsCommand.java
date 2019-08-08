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
        int page = 0;
        int size = 5;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        if (request.getParameter("size") != null) {
            size = Integer.parseInt(request.getParameter("size"));
        }

        request.setAttribute("activityRequests", activityRequestService.getAllActivityRequestsPageable(page, size));
        request.setAttribute("page", page);
        request.setAttribute("size", size);
        return "/activity-requests.jsp";
    }
}
