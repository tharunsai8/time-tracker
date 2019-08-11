package com.yurwar.trainingcourse.controller.command;

import com.yurwar.trainingcourse.controller.dto.ActivityDurationDTO;
import com.yurwar.trainingcourse.model.entity.User;
import com.yurwar.trainingcourse.model.service.ActivityService;
import com.yurwar.trainingcourse.util.validator.CompositeValidator;
import com.yurwar.trainingcourse.util.validator.PositiveOrZeroValidator;
import com.yurwar.trainingcourse.util.validator.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class MarkTimeCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private final ActivityService activityService;
    private final ResourceBundle rb = ResourceBundle.getBundle("i18n.messages");

    public MarkTimeCommand(ActivityService activityService) {
        this.activityService = activityService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("authUser");

        long activityId;
        int days;
        int hours;
        int minutes;
        try {
            activityId = Long.parseLong(request.getParameter("id"));
            days = Integer.parseInt(request.getParameter("days"));
            hours = Integer.parseInt(request.getParameter("hours"));
            minutes = Integer.parseInt(request.getParameter("minutes"));
        } catch (NumberFormatException e) {
            log.warn("Can not parse number from request parameter");
            return "/WEB-INF/error/404.jsp";
        }


        ActivityDurationDTO activityDurationDTO = new ActivityDurationDTO(days, hours, minutes);

        Map<String, String[]> validationErrorsMap = getValidationErrorsMap(activityDurationDTO);
        if (!validationErrorsMap.isEmpty()) {
            request.setAttribute("errors", validationErrorsMap);
            return new ActivitiesCommand(activityService).execute(request);
        }

        activityService.markTimeSpent(activityId, user, activityDurationDTO);

        return "redirect:/activities";
    }

    private Map<String, String[]> getValidationErrorsMap(ActivityDurationDTO activityDurationDTO) {
        Map<String, String[]> validationErrorsMap = new HashMap<>();
        CompositeValidator<Integer> daysValidator = new CompositeValidator<>(
                new PositiveOrZeroValidator(rb.getString("validation.activity.duration.days.positive_or_zero"))
        );
        CompositeValidator<Integer> hoursValidator = new CompositeValidator<>(
                new PositiveOrZeroValidator(rb.getString("validation.activity.duration.hours.positive_or_zero"))
        );
        CompositeValidator<Integer> minutesValidator = new CompositeValidator<>(
                new PositiveOrZeroValidator(rb.getString("validation.activity.duration.minutes.positive_or_zero"))
        );
        Result result = daysValidator.validate(activityDurationDTO.getDays());
        if (!result.isValid()) {
            validationErrorsMap.put("daysErrors", result.getMessage().split("\n"));
        }
        result = hoursValidator.validate(activityDurationDTO.getHours());
        if (!result.isValid()) {
            validationErrorsMap.put("hoursErrors", result.getMessage().split("\n"));
        }
        result = minutesValidator.validate(activityDurationDTO.getMinutes());
        if (!result.isValid()) {
            validationErrorsMap.put("minutesErrors", result.getMessage().split("\n"));
        }
        return validationErrorsMap;
    }
}
