package com.yurwar.trainingcourse.controller.command;

import com.yurwar.trainingcourse.controller.dto.UpdateUserDTO;
import com.yurwar.trainingcourse.model.entity.Authority;
import com.yurwar.trainingcourse.model.entity.User;
import com.yurwar.trainingcourse.model.service.UserService;
import com.yurwar.trainingcourse.util.CommandUtils;
import com.yurwar.trainingcourse.util.exception.UsernameNotUniqueException;
import com.yurwar.trainingcourse.util.validator.*;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Get user update page, validate inputa data and update user from user profile page
 *
 * @author Yurii Matora
 * @see User
 * @see UserService
 */
public class UserProfileUpdateCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private final UserService userService;
    private ResourceBundle rb;

    UserProfileUpdateCommand(UserService userService) {
        this.userService = userService;
    }

    /**
     * @param request User http request to server
     * @return name of page or redirect
     */
    @Override
    public String execute(HttpServletRequest request) {
        rb = ResourceBundle.getBundle("i18n.messages", CommandUtils.getLocaleFromSession(request));

        long id = CommandUtils.getUserFromSession(request).getId();

        User user = userService.getUserById(id);

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (!ObjectUtils.allNotNull(firstName, lastName, username, password)) {
            request.setAttribute("user", user);
            request.setAttribute("authorities", Authority.values());
            return "/WEB-INF/pages/profile-update.jsp";
        }

        UpdateUserDTO userDTO = UpdateUserDTO.builder()
                .id(id)
                .username(username)
                .firstName(firstName)
                .lastName(lastName)
                .password(password)
                .authorities(user.getAuthorities())
                .build();

        Map<String, String[]> validationErrorsMap = getValidationErrorsMap(userDTO);
        if (!validationErrorsMap.isEmpty()) {
            request.setAttribute("user", userDTO);
            request.setAttribute("errors", validationErrorsMap);
            return "/WEB-INF/pages/profile-update.jsp";
        }

        try {
            userService.updateUser(userDTO);
        } catch (UsernameNotUniqueException e) {
            e.printStackTrace();
            request.setAttribute("user", userDTO);
            request.setAttribute("usernameUniqueError", rb.getString("users.registration.login.not_unique"));
            return "/WEB-INF/pages/profile-update.jsp";
        }
        return "redirect:/profile";
    }

    private Map<String, String[]> getValidationErrorsMap(UpdateUserDTO userDTO) {
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
                new SizeOrBlankValidator(5, 39, rb.getString("validation.user.password.size"))
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