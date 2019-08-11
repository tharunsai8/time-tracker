package com.yurwar.trainingcourse.controller.command;

import com.yurwar.trainingcourse.model.entity.ActivityRequest;
import com.yurwar.trainingcourse.model.entity.ActivityRequestStatus;
import com.yurwar.trainingcourse.model.service.ActivityRequestService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class ActivityRequestApproveCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private final ActivityRequestService activityRequestService;

    public ActivityRequestApproveCommand(ActivityRequestService activityRequestService) {
        this.activityRequestService = activityRequestService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        long activityRequestId;
        try {
            activityRequestId = Long.parseLong(request.getParameter("id"));
        } catch (NumberFormatException e) {
            log.warn("Can not parse number from request parameter");
            return "/WEB-INF/error/404.jsp";
        }

        ActivityRequest activityRequest = activityRequestService
                .getActivityRequestById(activityRequestId);

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
