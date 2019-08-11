package com.yurwar.trainingcourse.controller.command;

import com.yurwar.trainingcourse.model.service.ActivityService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class ActivityDeleteCommand implements Command {
    private final ActivityService activityService;
    private static final Logger log = LogManager.getLogger();

    public ActivityDeleteCommand(ActivityService activityService) {
        this.activityService = activityService;
    }

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
