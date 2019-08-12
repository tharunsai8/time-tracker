package com.yurwar.trainingcourse.controller.command;

import com.yurwar.trainingcourse.model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Delete user from system
 *
 * @author Yurii Matora
 * @see com.yurwar.trainingcourse.model.entity.User
 * @see UserService
 */
public class UserDeleteCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private final UserService userService;

    UserDeleteCommand(UserService userService) {
        this.userService = userService;
    }

    /**
     * @param request User http request to server
     * @return name of page or redirect
     */
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
