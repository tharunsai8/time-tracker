package com.yurwar.trainingcourse.controller.command;

import com.yurwar.trainingcourse.model.service.ActivityService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Command that response to user requests and give page with all activities paginated
 * using activity service
 *
 * @author Yurii Matora
 * @see Command
 * @see com.yurwar.trainingcourse.model.entity.Activity
 * @see ActivityService
 */
public class ActivitiesCommand implements Command {
    /**
     * Log4j2 logger
     */
    private static final Logger log = LogManager.getLogger();
    private final ActivityService activityService;

    ActivitiesCommand(ActivityService activityService) {
        this.activityService = activityService;
    }

    /**
     * @param request User http request to server
     * @return name of page or redirect
     */
    @Override
    public String execute(HttpServletRequest request) {
        int page = 0;
        int size = 5;
        if (request.getParameter("page") != null) {
            try {
                page = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException e) {
                log.warn("Can not parse number from request parameter");
                return "/WEB-INF/error/404.jsp";
            }
        }
        if (request.getParameter("size") != null) {
            try {
                size = Integer.parseInt(request.getParameter("size"));
            } catch (NumberFormatException e) {
                log.warn("Can not parse number from request parameter");
                return "/WEB-INF/error/404.jsp";
            }
        }
        long numberOfRecords = activityService.getNumberOfRecords();
        long totalPages = (long) Math.ceil((double) numberOfRecords / size);

        request.setAttribute("activities", activityService.getAllActivitiesPageable(page, size));
        request.setAttribute("currentPage", page);
        request.setAttribute("pageSize", size);
        request.setAttribute("totalPages", totalPages);
        return "/WEB-INF/pages/activities.jsp";
    }
}
