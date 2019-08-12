package com.yurwar.trainingcourse.controller.command;

import com.yurwar.trainingcourse.model.service.ActivityService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Command that response to user requests and delete activity from database using activity service
 *
 * @author Yurii Matora
 * @see Command
 * @see com.yurwar.trainingcourse.model.entity.Activity
 * @see ActivityService
 */
public class ActivityDeleteCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private final ActivityService activityService;

    ActivityDeleteCommand(ActivityService activityService) {
        this.activityService = activityService;
    }

    /**
     * @param request User http request to server
     * @return name of page or redirect
     */
    @Override
    public String execute(HttpServletRequest request) {
        long activityId;
        try {
            activityId = Long.parseLong(request.getParameter("id"));
        } catch (NumberFormatException e) {
            log.warn("Can not parse number from request parameter");
            return "/WEB-INF/error/404.jsp";
        }
        activityService.deleteActivity(activityId);
        return "redirect:/activities";
    }
}
