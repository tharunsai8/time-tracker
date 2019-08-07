package com.yurwar.trainingcourse.controller.command;

import com.yurwar.trainingcourse.model.entity.Activity;
import com.yurwar.trainingcourse.model.entity.ActivityImportance;
import com.yurwar.trainingcourse.model.entity.ActivityStatus;
import com.yurwar.trainingcourse.model.service.ActivityService;
import org.apache.commons.lang3.ObjectUtils;

import javax.servlet.http.HttpServletRequest;

public class ActivityAddCommand implements Command {
    private final ActivityService activityService;

    public ActivityAddCommand(ActivityService activityService) {
        this.activityService = activityService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String importance = request.getParameter("importance");

        if (!ObjectUtils.allNotNull(name, description, importance)) {
            request.setAttribute("importanceLevels", ActivityImportance.values());
            return "/add-activity.jsp";
        }

        Activity activity = new Activity();
        activity.setName(name);
        activity.setDescription(description);
        activity.setImportance(ActivityImportance.valueOf(importance));
        activity.setStatus(ActivityStatus.PENDING);

        activityService.createActivity(activity);
        return "redirect:/activities";
    }
}
