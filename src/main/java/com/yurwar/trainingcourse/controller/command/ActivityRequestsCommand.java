package com.yurwar.trainingcourse.controller.command;

import com.yurwar.trainingcourse.model.service.ActivityRequestService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class ActivityRequestsCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private final ActivityRequestService activityRequestService;

    ActivityRequestsCommand(ActivityRequestService activityRequestService) {
        this.activityRequestService = activityRequestService;
    }

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
        long numberOfRecords = activityRequestService.getNumberOfRecords();
        long totalPages = (long) Math.ceil((double) numberOfRecords / size);


        request.setAttribute("activityRequests", activityRequestService.getAllActivityRequestsPageable(page, size));
        request.setAttribute("currentPage", page);
        request.setAttribute("pageSize", size);
        request.setAttribute("totalPages", totalPages);
        return "/WEB-INF/pages/activity-requests.jsp";
    }
}
