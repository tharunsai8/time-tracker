package com.yurwar.trainingcourse.controller.command;

import com.yurwar.trainingcourse.model.entity.User;
import com.yurwar.trainingcourse.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UserProfileCommand implements Command {
    private final UserService userService;

    public UserProfileCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("authUser");
        request.setAttribute("user", userService.findUserById(user.getId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid user id: " + user.getId())));
        return "/profile.jsp";
    }
}
