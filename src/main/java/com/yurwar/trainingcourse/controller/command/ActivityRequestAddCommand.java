package com.yurwar.trainingcourse.controller.command;

import com.yurwar.trainingcourse.model.entity.User;
import com.yurwar.trainingcourse.model.service.ActivityRequestService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ActivityRequestAddCommand implements Command {
    private ActivityRequestService activityRequestService;

    public ActivityRequestAddCommand(ActivityRequestService activityRequestService) {
        this.activityRequestService = activityRequestService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("authUser");
        long activityId = Long.parseLong(request.getParameter("id"));

        activityRequestService.makeAddActivityRequest(user.getId(), activityId);
        return "redirect:/activities";
    }
}
