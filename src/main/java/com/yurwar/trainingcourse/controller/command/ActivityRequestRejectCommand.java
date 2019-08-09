package com.yurwar.trainingcourse.controller.command;

import com.yurwar.trainingcourse.model.entity.ActivityRequest;
import com.yurwar.trainingcourse.model.entity.ActivityRequestStatus;
import com.yurwar.trainingcourse.model.service.ActivityRequestService;

import javax.servlet.http.HttpServletRequest;

public class ActivityRequestRejectCommand implements Command {
    private final ActivityRequestService activityRequestService;

    public ActivityRequestRejectCommand(ActivityRequestService activityRequestService) {
        this.activityRequestService = activityRequestService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        long activityRequestId = Long.parseLong(request.getParameter("id"));

        ActivityRequest activityRequest = activityRequestService.findActivityRequestById(activityRequestId);

        if (!activityRequest.getStatus().equals(ActivityRequestStatus.PENDING)) {
            return "redirect:/activities/request";
        }

        activityRequestService.rejectActivityRequest(activityRequestId);

        return "redirect:/activities/request";
    }
}
