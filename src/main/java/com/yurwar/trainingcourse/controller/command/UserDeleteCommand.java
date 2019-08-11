package com.yurwar.trainingcourse.controller.command;

import com.yurwar.trainingcourse.model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class UserDeleteCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private final UserService userService;

    public UserDeleteCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        long id;
        try {
            id = Long.parseLong(request.getParameter("id"));
        } catch (NumberFormatException e) {
            log.warn("Can not parse number from request parameter");
            return "/WEB-INF/error/404.jsp";
        }
        userService.deleteUserById(id);
        return "redirect:/users";
    }
}
