package com.yurwar.trainingcourse.controller.command;

import com.yurwar.trainingcourse.model.entity.User;
import com.yurwar.trainingcourse.model.service.ActivityRequestService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ActivityRequestCompleteCommand implements Command {
    private ActivityRequestService activityRequestService;

    public ActivityRequestCompleteCommand(ActivityRequestService activityRequestService) {
        this.activityRequestService = activityRequestService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("authUser");
        Long activityId = Long.valueOf(request.getParameter("id"));

        activityRequestService.makeCompleteActivityRequest(user.getId(), activityId);
        return "redirect:/activities";
    }
}
