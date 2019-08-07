package com.yurwar.trainingcourse.controller.command;

import com.yurwar.trainingcourse.model.entity.ActivityRequest;
import com.yurwar.trainingcourse.model.entity.ActivityRequestStatus;
import com.yurwar.trainingcourse.model.service.ActivityRequestService;

import javax.servlet.http.HttpServletRequest;

public class ActivityRequestApproveCommand implements Command {
    private ActivityRequestService activityRequestService;

    public ActivityRequestApproveCommand(ActivityRequestService activityRequestService) {
        this.activityRequestService = activityRequestService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        long activityRequestId = Long.parseLong(request.getParameter("id"));

        ActivityRequest activityRequest = activityRequestService
                .findActivityRequestById(activityRequestId);

        if (!activityRequest.getStatus().equals(ActivityRequestStatus.PENDING)) {
            return "redirect:/activities/request";
        }

        switch (activityRequest.getAction()) {
            case ADD:
                activityRequestService.approveAddActivityRequest(activityRequestId);
                break;
            case COMPLETE:
                activityRequestService.approveCompleteActivityRequest(activityRequestId);
                break;
        }

        return "redirect:/activities/request";
    }
}
