package com.yurwar.trainingcourse.controller.command;

import com.yurwar.trainingcourse.model.entity.Authority;
import com.yurwar.trainingcourse.model.entity.User;
import com.yurwar.trainingcourse.model.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

public class RegistrationCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private final UserService userService;

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

        User user = new User();
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(DigestUtils.md5Hex(password));
        user.setAuthorities(Collections.singleton(Authority.USER));

        log.info("User to be registered: " + user);

        if (userService.registerUser(user)) {
            log.info("User successfully registered");
            return "redirect:/login";
        } else {
            log.info("User can not be registered");
            request.setAttribute("error", true);
            return "/registration.jsp";
        }
    }
}
