package com.yurwar.trainingcourse.controller.command;

import com.yurwar.trainingcourse.model.entity.User;
import com.yurwar.trainingcourse.model.service.UserService;
import com.yurwar.trainingcourse.util.CommandUtils;

import javax.servlet.http.HttpServletRequest;

public class UserProfileCommand implements Command {
    private final UserService userService;

    UserProfileCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        User user = CommandUtils.getUserFromSession(request);
        request.setAttribute("user", userService.getUserById(user.getId()));
        return "/WEB-INF/pages/profile.jsp";
    }
}
