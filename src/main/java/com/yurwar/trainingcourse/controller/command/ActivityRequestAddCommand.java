package com.yurwar.trainingcourse.controller.command;

import com.yurwar.trainingcourse.model.entity.User;
import com.yurwar.trainingcourse.model.service.ActivityRequestService;
import com.yurwar.trainingcourse.util.CommandUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Add activity request from user to add activity to the database using activity request service
 *
 * @author Yurii Matora
 * @see com.yurwar.trainingcourse.model.entity.Activity
 * @see com.yurwar.trainingcourse.model.entity.ActivityRequest
 * @see ActivityRequestService
 */
public class ActivityRequestAddCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private final ActivityRequestService activityRequestService;

    ActivityRequestAddCommand(ActivityRequestService activityRequestService) {
        this.activityRequestService = activityRequestService;
    }

    /**
     * @param request User http request to server
     * @return name of page or redirect
     */
    @Override
    public String execute(HttpServletRequest request) {
        User user = CommandUtils.getUserFromSession(request);
        long activityId;
        try {
            activityId = Long.parseLong(request.getParameter("id"));
        } catch (NumberFormatException e) {
            log.warn("Can not parse number from request parameter");
            return "/WEB-INF/error/404.jsp";
        }

        activityRequestService.makeAddActivityRequest(user.getId(), activityId);
        return "redirect:/activities";
    }
}
