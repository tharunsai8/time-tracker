package com.yurwar.trainingcourse.controller.command;

import com.yurwar.trainingcourse.controller.dto.RegistrationUserDTO;
import com.yurwar.trainingcourse.model.service.UserService;
import com.yurwar.trainingcourse.util.exception.UsernameNotUniqueException;
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

public class RegistrationCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private final UserService userService;
    private final ResourceBundle rb = ResourceBundle.getBundle("i18n.messages");

    public RegistrationCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (!ObjectUtils.allNotNull(firstName, lastName, username, password)) {
            return "/registration.jsp";
        }

        RegistrationUserDTO userDTO = RegistrationUserDTO.builder()
                .firstName(firstName)
                .lastName(lastName)
                .username(username)
                .password(password)
                .build();


        Map<String, String[]> validationErrorsMap = getValidationErrorsMap(userDTO);
        if (!validationErrorsMap.isEmpty()) {
            request.setAttribute("user", userDTO);
            request.setAttribute("errors", validationErrorsMap);
            return "/registration.jsp";
        }

        try {
            userService.registerUser(userDTO);
        } catch (UsernameNotUniqueException e) {
            e.printStackTrace();
            request.setAttribute("user", userDTO);
            request.setAttribute("usernameUniqueError", rb.getString("users.registration.login.not_unique"));
            return "/registration.jsp";
        }
        return "redirect:/login";
    }

    private Map<String, String[]> getValidationErrorsMap(RegistrationUserDTO userDTO) {
        Map<String, String[]> validationErrorsMap = new HashMap<>();
        CompositeValidator<String> firstNameValidator = new CompositeValidator<>(
                new NotBlankValidator(rb.getString("validation.user.first_name.not_blank")),
                new SizeValidator(2, 50, rb.getString("validation.user.first_name.size"))
        );
        CompositeValidator<String> lastNameValidator = new CompositeValidator<>(
                new NotBlankValidator(rb.getString("validation.user.last_name.not_blank")),
                new SizeValidator(2, 50, rb.getString("validation.user.last_name.size"))
        );
        CompositeValidator<String> usernameValidator = new CompositeValidator<>(
                new NotBlankValidator(rb.getString("validation.user.username.not_blank")),
                new SizeValidator(5, 39, rb.getString("validation.user.username.size"))
        );
        CompositeValidator<String> passwordValidator = new CompositeValidator<>(
                new NotBlankValidator(rb.getString("validation.user.password.not_blank")),
                new SizeValidator(5, 39, rb.getString("validation.user.password.size"))
        );
        Result result = firstNameValidator.validate(userDTO.getFirstName());
        if (!result.isValid()) {
            validationErrorsMap.put("firstNameErrors", result.getMessage().split("\n"));
        }
        result = lastNameValidator.validate(userDTO.getLastName());
        if (!result.isValid()) {
            validationErrorsMap.put("lastNameErrors", result.getMessage().split("\n"));
        }
        result = usernameValidator.validate(userDTO.getUsername());
        if (!result.isValid()) {
            validationErrorsMap.put("usernameErrors", result.getMessage().split("\n"));
        }
        result = passwordValidator.validate(userDTO.getPassword());
        if (!result.isValid()) {
            validationErrorsMap.put("passwordErrors", result.getMessage().split("\n"));
        }
        return validationErrorsMap;
    }
}
