package com.yurwar.trainingcourse.controller.command;

import com.yurwar.trainingcourse.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class UsersCommand implements Command {
    private UserService userService;

    public UsersCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        request.setAttribute("users", userService.getAllUsers());
        return "/users.jsp";
    }
}
