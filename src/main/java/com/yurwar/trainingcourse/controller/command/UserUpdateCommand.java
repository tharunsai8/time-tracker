package com.yurwar.trainingcourse.controller.command;

import com.yurwar.trainingcourse.controller.dto.UpdateUserDTO;
import com.yurwar.trainingcourse.model.entity.Authority;
import com.yurwar.trainingcourse.model.entity.User;
import com.yurwar.trainingcourse.model.service.UserService;
import com.yurwar.trainingcourse.util.exception.UsernameNotUniqueException;
import com.yurwar.trainingcourse.util.validator.*;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;


public class UserUpdateCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private final UserService userService;
    private final ResourceBundle rb = ResourceBundle.getBundle("i18n.messages");

    public UserUpdateCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        long id = Long.parseLong(request.getParameter("id"));
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String[] authorities = request.getParameterValues("authorities");

        log.info("First name: {}, Last name: {}, Username: {}, Password: {}, Authorities: {}",
                firstName, lastName, username, password, authorities);


        if (!ObjectUtils.allNotNull(firstName, lastName, username, password)) {
            User user = userService.findUserById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user id: " + id));
            request.setAttribute("user", user);
            request.setAttribute("authorities", Authority.values());
            return "/update-user.jsp";
        }

        UpdateUserDTO userDTO = UpdateUserDTO.builder()
                .id(id)
                .username(username)
                .firstName(firstName)
                .lastName(lastName)
                .password(password)
                .authorities(extractAuthorities(authorities))
                .build();

        Map<String, String[]> validationErrorsMap = getValidationErrorsMap(userDTO);
        if (!validationErrorsMap.isEmpty()) {
            request.setAttribute("user", userDTO);
            request.setAttribute("authorities", Authority.values());
            request.setAttribute("errors", validationErrorsMap);
            return "/update-user.jsp";
        }

        try {
            userService.updateUser(userDTO);
        } catch (UsernameNotUniqueException e) {
            e.printStackTrace();
            request.setAttribute("user", userDTO);
            request.setAttribute("authorities", Authority.values());
            request.setAttribute("usernameUniqueError", rb.getString("users.registration.login.not_unique"));
            return "/update-user.jsp";
        }
        return "redirect:/users";
    }

    private Set<Authority> extractAuthorities(String[] authorities) {
        if (authorities != null) {
            return Arrays.stream(authorities).map(Authority::valueOf).collect(Collectors.toSet());
        } else {
            return Collections.emptySet();
        }
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
        CompositeValidator<Collection<Authority>> authoritiesValidator = new CompositeValidator<>(
                new CollectionMinSizeValidator(1, rb.getString("validation.user.authorities.size"))
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
        result = authoritiesValidator.validate(userDTO.getAuthorities());
        if (!result.isValid()) {
            validationErrorsMap.put("authoritiesErrors", result.getMessage().split("\n"));
        }
        return validationErrorsMap;
    }
}
