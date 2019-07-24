package com.yurwar.trainingcourse.controller.command;

import com.yurwar.trainingcourse.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class UserDeleteCommand implements Command {
    private UserService userService;

    public UserDeleteCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        long id = Long.parseLong(request.getParameter("id"));
        userService.deleteUserById(id);
        return "redirect:users";
    }
}
