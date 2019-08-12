package com.yurwar.trainingcourse.controller.command;

import com.yurwar.trainingcourse.controller.dto.ActivityDTO;
import com.yurwar.trainingcourse.model.entity.Activity;
import com.yurwar.trainingcourse.model.entity.ActivityImportance;
import com.yurwar.trainingcourse.model.entity.ActivityStatus;
import com.yurwar.trainingcourse.model.service.ActivityService;
import com.yurwar.trainingcourse.util.CommandUtils;
import com.yurwar.trainingcourse.util.validator.CompositeValidator;
import com.yurwar.trainingcourse.util.validator.NotBlankValidator;
import com.yurwar.trainingcourse.util.validator.Result;
import com.yurwar.trainingcourse.util.validator.SizeValidator;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ActivityAddCommand implements Command {
    public static final Logger log = LogManager.getLogger();
    private final ActivityService activityService;
    private ResourceBundle rb;

    public ActivityAddCommand(ActivityService activityService) {
        this.activityService = activityService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        rb = ResourceBundle.getBundle("i18n.messages", CommandUtils.getLocaleFromSession(request));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String importance = request.getParameter("importance");

        if (!ObjectUtils.allNotNull(name, description, importance)) {
            request.setAttribute("importanceLevels", ActivityImportance.values());
            return "/WEB-INF/pages/add-activity.jsp";
        }

        Activity activity = new Activity();
        activity.setName(name);
        activity.setDescription(description);
        activity.setImportance(ActivityImportance.valueOf(importance));
        activity.setStatus(ActivityStatus.PENDING);

        ActivityDTO activityDTO = ActivityDTO.builder()
                .name(name)
                .description(description)
                .importance(ActivityImportance.valueOf(importance))
                .build();

        Map<String, String[]> validationErrorsMap = getValidationErrorsMap(activityDTO);
        if (!validationErrorsMap.isEmpty()) {
            request.setAttribute("activity", activityDTO);
            request.setAttribute("importanceLevels", ActivityImportance.values());
            request.setAttribute("errors", validationErrorsMap);
            return "/WEB-INF/pages/add-activity.jsp";
        }
        activityService.createActivity(activityDTO);
        return "redirect:/activities";
    }

    private Map<String, String[]> getValidationErrorsMap(ActivityDTO activityDTO) {
        Map<String, String[]> validationErrorMap = new HashMap<>();
        CompositeValidator<String> activityDescriptionValidator = new CompositeValidator<>(
                new SizeValidator(0, 500, rb.getString("validation.activity.description.size"))
        );
        CompositeValidator<String> activityNameValidator = new CompositeValidator<>(
                new SizeValidator(5, 100, rb.getString("validation.activity.name.size")),
                new NotBlankValidator(rb.getString("validation.activity.name.not_blank")));
        Result result = activityNameValidator.validate(activityDTO.getName());
        if (!result.isValid()) {
            validationErrorMap.put("nameErrors", result.getMessage().split("\n"));
        }
        result = activityDescriptionValidator.validate(activityDTO.getDescription());
        if (!result.isValid()) {
            validationErrorMap.put("descriptionErrors", result.getMessage().split("\n"));
        }
        return validationErrorMap;
    }
}
