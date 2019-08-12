package com.yurwar.trainingcourse.controller.command;

import com.yurwar.trainingcourse.model.entity.ActivityRequest;
import com.yurwar.trainingcourse.model.entity.ActivityRequestStatus;
import com.yurwar.trainingcourse.model.service.ActivityRequestService;

import javax.servlet.http.HttpServletRequest;

/**
 * Reject user activity request
 *
 * @author Yurii Matora
 * @see ActivityRequest
 * @see ActivityRequestService
 */
public class ActivityRequestRejectCommand implements Command {
    private final ActivityRequestService activityRequestService;

    ActivityRequestRejectCommand(ActivityRequestService activityRequestService) {
        this.activityRequestService = activityRequestService;
    }

    /**
     * @param request User http request to server
     * @return name of page or redirect
     */
    @Override
    public String execute(HttpServletRequest request) {
        long activityRequestId = Long.parseLong(request.getParameter("id"));

        ActivityRequest activityRequest = activityRequestService.getActivityRequestById(activityRequestId);

        if (!activityRequest.getStatus().equals(ActivityRequestStatus.PENDING)) {
            return "redirect:/activities/request";
        }

        activityRequestService.rejectActivityRequest(activityRequestId);

        return "redirect:/activities/request";
    }
}
