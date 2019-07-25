package com.yurwar.trainingcourse.controller.command;

import com.yurwar.trainingcourse.model.entity.User;
import com.yurwar.trainingcourse.model.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.Optional;

public class LoginCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private UserService userService;

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (Objects.isNull(username) || Objects.isNull(password)) {
            return "/login.jsp";
        }
        log.info("User try to log in with username: " + username + " and password: " + password );

        Optional<User> userOptional = userService.getUserByUsername(username);
        if (!userOptional.isPresent()) {
            log.warn("No such user " + username + " in database");
            request.setAttribute("error", true);
            return "/login.jsp";
        }

        User user = userOptional.get();

        if (user.getPassword().equals(DigestUtils.md5Hex(password))) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("username", user.getUsername());
            session.setAttribute("role", user.getRole());
            log.info("User " + username + " successfully logged in");
            return "redirect:/index";
        } else {
            log.info("Invalid credentials for user " + username);
            request.setAttribute("error", true);
            return "/login.jsp";
        }
    }
}
