package com.yurwar.trainingcourse.controller.command;

import com.yurwar.trainingcourse.model.entity.User;
import com.yurwar.trainingcourse.model.service.ActivityService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class MarkTimeCommand implements Command {
    private final ActivityService activityService;

    public MarkTimeCommand(ActivityService activityService) {
        this.activityService = activityService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        //TODO Check on null before parse
        //TODO Check on negative
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("authUser");

        long activityId = Long.parseLong(request.getParameter("id"));
        int days = Integer.parseInt(request.getParameter("days"));
        int hours = Integer.parseInt(request.getParameter("hours"));
        int minutes = Integer.parseInt(request.getParameter("minutes"));


        activityService.markTimeSpent(activityId, user, days, hours, minutes);

        return "redirect:/activities";
    }
}
